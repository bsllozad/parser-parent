
--Query for find all ips records with request amount higher that to threshold without dates filter
SELECT records.ip, records.cant FROM (
	SELECT 
		asl.ip, 
		COUNT(*) as cant,
		asl.request_date
	FROM access_server_log asl
	GROUP BY asl.ip
) records
WHERE records.cant > 200


-- Query for find ip one with blocked message
SELECT pl.ip,
	pl.comments_blocked
FROM parser_log pl
WHERE pl.ip = '192.168.129.191'