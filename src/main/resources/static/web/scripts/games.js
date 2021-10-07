const app = Vue.createApp({
    data() {
        return {
            playerCurrent: [],
            games: [],
            players: [],

            // login
            user: "",
            password: "",
            // register
            userR: "",
            passwordR: "",
            email: "",
            passwordRepeat: "",

            sesionR: null,
        }
    },
    created() {
        axios.get("/api/player/current")
            .then(res => {
                this.playerCurrent = res.data;

            })
            .catch(err => console.log(err))

        axios.get("/api/games")
            .then(res => {
                this.sesionR = res.data
                this.games = res.data.filter(e => e.player.id != this.playerCurrent.id).sort((a, b) => a.id - b.id)
                console.log(this.games)
            })
            .catch(err => console.log(err))

        axios.get("/api/games/players")
            .then(res => {
                this.players = res.data.sort((a, b) => b.total - a.total)
                console.log(this.players)
            })
            .catch(err => console.log(err))
    },
    methods: {
        login() {
            axios.post("/api/login", "user=" + this.user + "&password=" + this.password)
                .then(res => {
                    console.log("Sing In")
                    location.href = "games.html"
                })
                .catch(err => swal("Error", "User no exist", "error"))
        },
        signOut() {
            axios.post("/api/logout")
                .then(res => {
                    location.href = "index.html";
                })
        },
        register() {
            if (this.passwordR == this.passwordRepeat) {
                axios.post("/api/players", "user=" + this.userR + "&email=" + this.email + "&password=" + this.passwordR)
                    .then(res => {
                        swal("Good job!", "You clicked the button!", "success").then(res => location.href = "games.html")

                    })
                    .catch(err => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'User o email in use',
                        })
                    })
            } else {
                swal("Error", "Password no ", "info");
            }
        },
        createGame() {
            axios.post("/api/games/current/game")
                .then(res => {
                    let gpId = res.data
                    console.log(gpId.gpId)
                    location.href = "game.html?gp=" + gpId.gpId;
                })
        },
        joinGame(idGame) {
            console.log(idGame)
            axios.post("/api/games/" + idGame + "/players")
                .then(res => {
                    let gpId = res.data
                    console.log(gpId.gpId)
                    location.href = "game.html?gp=" + gpId.gpId;
                })
                .catch(err => console.log(err))
        },
        reload() {
            location.reload()
        },
        toggleForm() {
            const container = document.querySelector('.container');
            container.classList.toggle('active');
        }
    },
    computed: {}
})
app.mount("#app")

window.addEventListener('load', () => {
    const contenedor_loader = document.querySelector('.contenedor')
    contenedor_loader.style.opacity = 0
    contenedor_loader.style.visibility = 'hidden'
})