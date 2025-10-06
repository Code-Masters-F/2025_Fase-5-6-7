-- ========================================
-- SCRIPT DE LIMPEZA TOTAL GENÉRICA - ORACLE
-- ========================================
-- Este script remove TODAS as tabelas do schema atual
-- independentemente de quais existem no momento

SET SERVEROUTPUT ON;

DECLARE
    e EXCEPTION;
    PRAGMA EXCEPTION_INIT(e, -00942);
    v_count NUMBER := 0;
    v_total_dropadas NUMBER := 0;
    v_total_erro NUMBER := 0;
    v_sql VARCHAR2(4000);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== LIMPEZA TOTAL DO SCHEMA ===');
    DBMS_OUTPUT.PUT_LINE('Usuário atual: ' || USER);
    DBMS_OUTPUT.PUT_LINE('');
    
    -- Primeiro, verificar quantas tabelas existem
    SELECT COUNT(*) INTO v_count FROM user_tables;
    DBMS_OUTPUT.PUT_LINE('📊 Total de tabelas encontradas: ' || v_count);
    
    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('✅ Nenhuma tabela encontrada no schema. Nada para remover.');
        RETURN;
    END IF;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('🔍 Verificando sessões bloqueadoras...');
    
    -- Tentar matar sessões que podem estar bloqueando as tabelas
    FOR rec IN (SELECT DISTINCT s.sid, s.serial#, s.username, s.program
                FROM v$session s, v$locked_object lo, dba_objects o
                WHERE s.sid = lo.session_id
                AND lo.object_id = o.object_id
                AND o.owner = USER
                AND o.object_type = 'TABLE'
                AND s.username != USER) LOOP
        
        DBMS_OUTPUT.PUT_LINE('⚠️ Sessão bloqueadora encontrada: SID=' || rec.sid || ', Serial#=' || rec.serial# || ', User=' || rec.username);
        
        BEGIN
            v_sql := 'ALTER SYSTEM KILL SESSION ''' || rec.sid || ',' || rec.serial# || ''' IMMEDIATE';
            EXECUTE IMMEDIATE v_sql;
            DBMS_OUTPUT.PUT_LINE('✅ Sessão terminada: ' || rec.sid || ',' || rec.serial#);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('❌ Erro ao terminar sessão: ' || SQLERRM);
        END;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('=== REMOVENDO TODAS AS TABELAS ===');
    DBMS_OUTPUT.PUT_LINE('');
    
    -- Listar todas as tabelas que serão removidas
    DBMS_OUTPUT.PUT_LINE('📋 Tabelas que serão removidas:');
    FOR rec IN (SELECT table_name FROM user_tables ORDER BY table_name) LOOP
        DBMS_OUTPUT.PUT_LINE('  - ' || rec.table_name);
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('🗑️ Iniciando remoção...');
    
    -- Loop principal - remover todas as tabelas
    FOR rec IN (SELECT table_name FROM user_tables ORDER BY table_name) LOOP
        
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Removendo: ' || rec.table_name);
        
        -- Tentar várias abordagens para remover a tabela
        BEGIN
            -- Método 1: Drop normal com CASCADE CONSTRAINTS
            EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS';
            DBMS_OUTPUT.PUT_LINE('  ✅ Removida com sucesso (método normal)');
            v_total_dropadas := v_total_dropadas + 1;
            
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('  ⚠️ Método normal falhou: ' || SQLERRM);
                
                -- Método 2: Tentar novamente com PURGE
                BEGIN
                    EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS PURGE';
                    DBMS_OUTPUT.PUT_LINE('  ✅ Removida com sucesso (método PURGE)');
                    v_total_dropadas := v_total_dropadas + 1;
                    
                EXCEPTION
                    WHEN OTHERS THEN
                        DBMS_OUTPUT.PUT_LINE('  ⚠️ Método PURGE falhou: ' || SQLERRM);
                        
                        -- Método 3: Tentar com PURGE apenas
                        BEGIN
                            EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' PURGE';
                            DBMS_OUTPUT.PUT_LINE('  ✅ Removida com sucesso (PURGE simples)');
                            v_total_dropadas := v_total_dropadas + 1;
                            
                        EXCEPTION
                            WHEN OTHERS THEN
                                DBMS_OUTPUT.PUT_LINE('  ❌ Todos os métodos falharam: ' || SQLERRM);
                                v_total_erro := v_total_erro + 1;
                                DBMS_OUTPUT.PUT_LINE('  💡 Comando manual: DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS PURGE;');
                        END;
                END;
        END;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('=== LIMPEZA DE OBJETOS ORFÃOS ===');
    
    -- Limpar objetos que podem ter ficado órfãos
    BEGIN
        EXECUTE IMMEDIATE 'PURGE RECYCLEBIN';
        DBMS_OUTPUT.PUT_LINE('✅ Recyclebin limpo');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('⚠️ Erro ao limpar recyclebin: ' || SQLERRM);
    END;
    
    -- Tentar limpar constraints órfãs
    BEGIN
        FOR rec IN (SELECT constraint_name FROM user_constraints WHERE table_name IS NULL) LOOP
            BEGIN
                EXECUTE IMMEDIATE 'ALTER TABLE ' || USER || '.' || rec.constraint_name || ' DROP CONSTRAINT ' || rec.constraint_name;
            EXCEPTION
                WHEN OTHERS THEN NULL;
            END;
        END LOOP;
        DBMS_OUTPUT.PUT_LINE('✅ Constraints órfãs limpas');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('ℹ️ Nenhuma constraint órfã encontrada');
    END;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('=== RESUMO FINAL ===');
    DBMS_OUTPUT.PUT_LINE('📊 Tabelas processadas: ' || v_count);
    DBMS_OUTPUT.PUT_LINE('✅ Tabelas removidas com sucesso: ' || v_total_dropadas);
    DBMS_OUTPUT.PUT_LINE('❌ Tabelas com erro: ' || v_total_erro);
    
    -- Verificar se ainda existem tabelas
    SELECT COUNT(*) INTO v_count FROM user_tables;
    
    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('🎉 SUCESSO! Todas as tabelas foram removidas!');
        DBMS_OUTPUT.PUT_LINE('✅ Schema completamente limpo e pronto para novo DDL.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('⚠️ Ainda existem ' || v_count || ' tabelas no schema:');
        
        FOR rec IN (SELECT table_name FROM user_tables ORDER BY table_name) LOOP
            DBMS_OUTPUT.PUT_LINE('  - ' || rec.table_name);
        END LOOP;
        
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('💡 Execute manualmente os comandos abaixo para as tabelas restantes:');
        FOR rec IN (SELECT table_name FROM user_tables ORDER BY table_name) LOOP
            DBMS_OUTPUT.PUT_LINE('DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS PURGE;');
        END LOOP;
    END IF;
    
END;
/

-- Verificação final das tabelas restantes
SELECT 'Tabelas restantes no schema:' as info FROM dual;
SELECT table_name, 
       CASE WHEN num_rows IS NULL THEN 'N/A' ELSE TO_CHAR(num_rows) END as num_rows
FROM user_tables 
ORDER BY table_name;

-- Verificar outros objetos que podem ter restado
SELECT 'Outros objetos no schema:' as info FROM dual;
SELECT object_type, COUNT(*) as quantidade
FROM user_objects 
WHERE object_type IN ('SEQUENCE', 'VIEW', 'PROCEDURE', 'FUNCTION', 'PACKAGE', 'TRIGGER')
GROUP BY object_type
ORDER BY object_type;
