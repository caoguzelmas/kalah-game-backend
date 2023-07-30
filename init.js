db = db.getSiblingDB('kalah-db');

db.createUser(
    {
        user: "kalah-player123",
        pwd: "pwd123",
        roles: [
            {
                role: "readWrite",
                db: "kalah-db"
            }
        ]
    }
);
db.createCollection('game');