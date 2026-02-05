
ID=95da973f-803a-4873-9e9b-8ab2fc584753

.PHONY: post
post:
	curl -X POST http://localhost:8080/user \
	  -H "Content-Type: application/json" \
	  -d "{\"firstname\": \"John\", \"lastname\": \"Doe\", \"age\": 30}"
	echo ""

# make put ID=07e9593a-d59e-4065-b637-800f6e4817c4
.PHONY: put
put:
	curl -X PUT http://localhost:8080/user/$(ID) \
    	  -H "Content-Type: application/json" \
    	  -d "{\"id\": \"$(ID)\", \"firstname\": \"John\", \"lastname\": \"Doe\", \"age\": 100}"
	echo ""

# make delete ID=07e9593a-d59e-4065-b637-800f6e4817c4
.PHONY: delete
delete:
	curl -X DELETE http://localhost:8080/user/$(ID)
	echo ""


.PHONY: getall
getall:
	curl -X GET http://localhost:8080/user #?id=550e8400-e29b-41d4-a716-446655440000
	echo ""




.PHONY: errpost
errpost:
	curl -X POST http://localhost:8080/user \
	  -H "Content-Type: application/json" \
	  -d "{\"lastname\": \"Doe\", \"age\": 30}"

.PHONY: del
del:
	docker-compose down -v testservice_postgres

.PHONY: redis
redis:
	redis-cli -h 127.0.0.1 -p 6379 -a 1111
