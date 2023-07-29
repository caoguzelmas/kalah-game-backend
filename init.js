db = db.getSiblingDB('kalah-db');

db.createUser(
    {
        user: "kalah-player",
        pwd: "pwd123",
        roles: [
            {
                role: "readWrite",
                db: "kalah-db"
            }
        ]
    }
);
db.createCollection('games');
db.games.insertOne(
    {
        "_id": "11111111",
        "firstPlayer": {"playerId": {"$numberInt": "1"}, "isActivePlayer": true},
        "secondPlayer": {"playerId": {"$numberInt": "2"}, "isActivePlayer": false},
        "gameBoard": {
            "houses": [{
                "houseId": {"$numberInt": "0"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "0"}
            }, {
                "houseId": {"$numberInt": "1"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "5"}
            }, {
                "houseId": {"$numberInt": "2"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "5"}
            }, {
                "houseId": {"$numberInt": "3"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "5"}
            }, {
                "houseId": {"$numberInt": "4"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "5"}
            }, {
                "houseId": {"$numberInt": "5"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "6"},
                "ownedPlayerId": {"$numberInt": "1"},
                "numberOfStones": {"$numberInt": "0"}
            }, {
                "houseId": {"$numberInt": "7"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "8"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "9"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "10"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "11"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "12"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "4"}
            }, {
                "houseId": {"$numberInt": "13"},
                "ownedPlayerId": {"$numberInt": "2"},
                "numberOfStones": {"$numberInt": "0"}
            }]
        },
        "gameVariation": {
            "flowsCounterClockwise": true,
            "emptyCaptureEnabled": true,
            "remainingStonesInsertionEnabled": true
        },
        "createDate": {"$date": {"$numberLong": "1654944621809"}},
        "isSuccess": true,
        "message": "",
        "_class": "com.caoguzelmas.kalah.model.Game"
    }
);