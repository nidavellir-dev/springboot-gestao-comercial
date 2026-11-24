INSERT INTO estoque (produto_id,
                     quantidade_actual,
                     quantidade_minima)
SELECT p.id,
       0,
       0
FROM produto p
WHERE NOT EXISTS (SELECT 1
                  FROM estoque e
                  WHERE e.produto_id = p.id);