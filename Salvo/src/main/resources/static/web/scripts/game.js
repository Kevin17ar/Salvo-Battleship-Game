const app = Vue.createApp({
    data() {
        return {
            player: [],
            idPLayer: 0,

            salvosPlayer2: [],
            user2: "Esperando enemigo",

            sinks: [],

            ships: [],
            shipsPlayer1: [],
            salvos: [],
            shipsPlayer2: [],

            thead: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            tbody: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
            idGame: null,
            status: "",

            shipsType: ["CARRIER", "BATTLESHIP", "SUBMARINE", "DESTROYER", "BOAT"],
            shipsSend: [],
            lengthShip: null,
            shipType: "",
            position: [],
            vertical: false,
            shoot: [],

            showShoot: true,
            showStar: true,
        }
    },
    created() {
        const urlParams = new URLSearchParams(window.location.search);
        const myParam = urlParams.get('gp');
        console.log(myParam);
        axios.get("/api/games_view/" + myParam)
            .then(res => {
                this.idGame = res.data.idGame;
                this.player = res.data.player;
                this.idPlayer = this.player.id;
                this.ships = res.data.ships;
                this.sinks = res.data.sinks;
                this.status = res.data.status;
                this.salvos = res.data.salvos.sort((a, b) => parseInt(a.turn) - parseInt(b.turn))
                console.log(res.data)

                axios.get("/api/games/" + this.idGame)
                    .then(res => {
                        let players = res.data.gamePlayers.sort((a, b) => parseInt(a.id) - parseInt(b.id))
                        console.log(players)
                        let player2 = players.filter(e => e.player.id != this.idPlayer)
                        this.user2 = player2[0].player.user
                        this.salvosPlayer2 = player2[0].salvos

                        player2[0].ships.forEach(e => {
                            e.locations.forEach(id => {
                                this.shipsPlayer2.push(id)
                            });
                        })
                        console.log(this.shipsPlayer2)
                    })
                    .catch(err => console.log(err))
            })
            .catch(err => console.log(err))
    },
    methods: {
        sendLocationShips() {
            console.log(this.shipsSend)
            if (this.shipsSend.length == 5) {
                const urlParams = new URLSearchParams(window.location.search);
                const myParam = urlParams.get('gp');

                axios.post("/api/games/player/" + myParam + "/ships", (this.shipsSend))
                    .then(res => {
                        this.showStar = false;
                        this.traerUser2();
                    })
                    .catch(err => console.log(err))
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Coloque los 5 barcos',
                })
            }
        },
        changeShip(ship) {
            console.log(ship)
            this.shipType = ship;
            console.log(this.shipType)
        },
        idTableShipSelect(idTable) {
            if (this.shipsSend.length < 5) {
                let number = parseFloat(idTable.slice(1));
                let charter = idTable.slice(0, 1);
                // console.log(charter);
                // console.log(number)
                // console.log(this.shipType)
                switch (this.shipType) {
                    case "CARRIER":
                        if (this.vertical) {
                            this.addChart("CARRIER", number, charter, 5)
                        } else {
                            if (number + 5 <= 11) {
                                this.addNumber("CARRIER", number, charter, 5)
                            }
                        }
                        break;
                    case "BATTLESHIP":
                        if (this.vertical) {
                            this.addChart("BATTLESHIP", number, charter, 4)
                        } else {
                            if (number + 4 <= 11) {
                                this.addNumber("BATTLESHIP", number, charter, 4)
                            }
                        }
                        break;
                    case "SUBMARINE":
                        if (this.vertical) {
                            this.addChart("SUBMARINE", number, charter, 3)
                        } else {
                            if (number + 3 <= 11) {
                                this.addNumber("SUBMARINE", number, charter, 3)
                            }
                        }
                        break;
                    case "DESTROYER":
                        if (this.vertical) {
                            this.addChart("DESTROYER", number, charter, 3)
                        } else {
                            if (number + 3 <= 11) {
                                this.addNumber("DESTROYER", number, charter, 3)
                            }
                        }
                        break;
                    case "BOAT":
                        if (this.vertical) {
                            this.addChart("BOAT", number, charter, 2)
                        } else {
                            if (number + 2 <= 11) {
                                this.addNumber("BOAT", number, charter, 2)
                            }
                        }
                        break;
                }
            }
        },
        addNumber(type, number, charter, length) {
            let array = [];
            for (var i = number; i < number + length; i++) {
                let position = charter + i;
                array.push(position);
            }
            let arrayF = [];
            array.forEach(e => {
                if (!this.position.includes(e)) {
                    arrayF.push(e)
                }
            })
            if (arrayF.length == length) {
                this.shipsSend.push({ type: type, locations: arrayF })
                this.shipsType.splice(this.shipsType.indexOf(type), 1)
                this.shipType = "";
                arrayF.forEach(e => {
                    this.position.push(e);
                    let idElement = document.getElementById(e);
                    idElement.style.background = "red";
                })
            }
        },
        addChart(type, number, charter, length) {
            let letra = charter;
            let num = letra.charCodeAt(0);
            let array = []
            for (let i = num; i < num + length; i++) {
                let text = String.fromCharCode(i)
                array.push(text)
            }
            let arrayR = []
            array.forEach(e => {
                let position = e + number;
                arrayR.push(position);
            })
            console.log(arrayR)
            let arrayI = []
            arrayR.forEach(e => {
                if (!!document.getElementById(e) && !this.position.includes(e)) {
                    arrayI.push(e)
                }
            })
            console.log(arrayI)
            if (arrayI.length == length) {
                this.shipsSend.push({ type: type, locations: arrayI })
                this.shipsType.splice(this.shipsType.indexOf(type), 1)
                arrayI.forEach(e => {
                    this.position.push(e);
                    let idElement = document.getElementById(e);
                    idElement.style.background = "red";
                })
            } else {
                console.log("no nay lugar")
            }
        },
        selectSalvo(idTable) {
            if (this.shoot.length < 5) {
                if (this.existInSalvos(idTable).length == 0 && !this.shoot.includes(idTable)) {
                    this.shoot.push(idTable);
                    let idElement = document.getElementById(idTable);
                    idElement.style.background = "red";
                }
            }
        },
        sendSalvos() {
            if (this.shoot.length == 5) {
                let endSalvo = 1
                if (this.salvos.length != 0) {
                    endSalvo = this.salvos[this.salvos.length - 1].turn + 1;
                }
                let positions = []
                this.shoot.forEach(e => {
                    positions.push(e.slice(1, 4))
                })
                const urlParams = new URLSearchParams(window.location.search);
                const myParam = urlParams.get('gp');
                axios.post("/api/games/players/" + myParam + "/salvos", ({
                        turn: endSalvo,
                        locations: positions,
                    }))
                    .then(res => {
                        this.showShoot = false;
                        this.getStatusTimeOut();
                    })
                    .catch(err => console.log(err))
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Seleccione 5 disparos',
                })
            }
        },
        existInSalvos(idTable) {
            let array = []
            this.salvos.forEach(e => {
                if (e.locations.includes(idTable)) {
                    array.push(idTable)
                }
            })
            return array;
        },
        getStatusTimeOut() {
            setTimeout(this.getStatus(), 10000)
        },
        traerUser2() { //funcion timeOut, solo si si nop hay player 2
            if (this.user2 == "Esperando enemigo") {
                timerId = setTimeout(this.findUser2(), 10000)
            }
            if (this.user2 != "Esperando enemigo") {
                location.reload();
            }
        },
        findUser2() { //se busca al player 2
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('gp');
            console.log(myParam);
            axios.get("/api/games_view/" + myParam)
                .then(res => {
                    var idGame = res.data.idGame;
                    var player = res.data.player
                    var idPlayer = player.id;
                    axios.get("/api/games/" + idGame)
                        .then(res => {
                            let players = res.data.gamePlayers.sort((a, b) => parseInt(a.id) - parseInt(b.id))
                            if (players.length <= 1) { //si no hay plaer 2 que reinice el la funion timeout
                                this.findUser2();
                            }
                            if (players.length > 1) { //si encuentra player2 verifica si coloco barcos, en caso contrario reinicia timeout
                                let player2 = players.filter(e => e.player.id != idPlayer)
                                if (player2[0].ships.length == 5) {
                                    location.reload()
                                } else {
                                    this.traerUser2()
                                }
                            }
                        })
                        .catch(err => console.log(err))
                })
        },
        getStatus() {
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('gp');
            console.log(myParam);
            axios.get("/api/games_view/" + myParam)
                .then(res => {
                    console.log(res.data)
                    var status = res.data.status
                    console.log(status)
                    if (status == "wait_salvos_enemy") {
                        this.getStatusTimeOut();
                    }
                    if (status == "shoot") {
                        location.reload();
                    }
                    if (status == "loser" || status == "winner" || status == "draw") {
                        this.scoreSave(status)
                            // location.reload();
                    }
                })
                .catch(err => console.log(err))
        },
        scoreSave(status) {
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('gp');

            axios.post("/api/games/over/" + myParam, "status=" + status)
                .then(res => {
                    location.reload();
                })
                .catch(err => console.log(err))
        },
        backHome() {
            location.href = "/web/games.html"
        }
    },
    computed: {
        showStarButton() {
            if (this.ships.length == 5) {
                return false;
            }
            return true;
        },
        showShootButton() {
            if (this.ships.length == 5) {
                return true;
            }
            return false;
        },
        showSalvoTable() {
            if (this.ships.length == 0) {
                return false;
            }
            return true;
        },
        color() {
            console.log(this.ships)
            this.ships.forEach(element => {
                element.locations.forEach(id => {
                    this.shipsPlayer1.push(id)
                        // console.log(this.shipsPlayer1)
                    var id = document.getElementById(id);
                    id.style.background = "blue";
                })
            })
        },
        player2salvos() {
            this.salvosPlayer2.forEach(element => {
                let turn = element.turn

                element.locations.forEach(id => {
                    // let id2 = id.slice(1, 4)
                    if (this.shipsPlayer1.includes(id)) {
                        var elemntId = document.getElementById(id);
                        elemntId.style.backgroundColor = "red";
                        elemntId.innerHTML = turn;
                    } else {
                        var elemntId = document.getElementById(id);
                        elemntId.style.backgroundColor = "coral";
                        elemntId.innerHTML = "X";
                    }
                })
            })
        },
        salvo() {
            this.salvos.forEach(elemnt => {
                let turn = elemnt.turn
                elemnt.locations.forEach(id => {
                    let idS = "S" + id
                    if (this.shipsPlayer2.includes(id)) {
                        var id = document.getElementById(idS);
                        id.style.background = "red";
                        id.innerHTML = turn;
                    } else {
                        var id = document.getElementById(idS);
                        id.style.background = "green";
                        id.innerHTML = "X";
                    }
                })
            })
        },
    }
})
app.mount("#app")