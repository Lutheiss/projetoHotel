projeto hotel - backend

como rodar:
1) precisa de: jdk 17+, maven, postgresql 16+
2) crie o banco e o usuário (exemplo):
   - database: projeto-hotel
   - user: hotel
   - password: hotel
3) ative a extensão unaccent no banco(usei por conta que não buscava sem acentuação na busca):
   \c hotel
   CREATE EXTENSION IF NOT EXISTS unaccent;
4) configure src/main/resources/application.properties com a senha do postgre
5) rodar:
   ./mvnw spring-boot:run
   (api em http://localhost:8080)

endpoints principais:

hóspedes (base /api/hospedes)
- POST    /api/hospedes
- GET     /api/hospedes
- GET     /api/hospedes/{id}
- GET     /api/hospedes/buscar?q=...
- GET     /api/hospedes/{id}/valores
- PUT     /api/hospedes/{id}
- DELETE  /api/hospedes/{id}

check-ins (base /api/checkIns)
- POST    /api/checkIns
- GET     /api/checkIns
- GET     /api/checkIns/{id}
- GET     /api/checkIns/no-hotel
- GET     /api/checkIns/historico
- PUT     /api/checkIns/{id}/checkout

testes via postman:
- importar a collection do git

