var array = [
    [0,0,0],
    [0,0,0],
    [0,0,0]
];
 

const jugador1 = 1; // En el mode individual, jugador1 es l'huma
const jugador2 = 2; // En el mode individual, jugador2 es AI.
//Jugadro que inicia partida
var jugador = jugador1; 
var gameOver=false;

//HTML ELEMENTS
    //fi partida mosrtrar guanyador
var endgame = document.querySelector(".endgame");
var text_endgame = document.querySelector("#text");
    //marcador 
var scoreboard_1 = document.querySelector("#p1");
var nTies = document.querySelector("#ties");
var scoreboard_2 = document.querySelector("#p2");
    //tauler de joc
var tablero = document.getElementById("tablero");
    //modos de joc
var swap = document.querySelector(".swapMode");
var display_easy = document.getElementById("EasyMode");
var display_difficult = document.getElementById("DifficultMode");
var display_hard = document.getElementById("HardMode");
var display_multiplayer = document.getElementById("Multiplayer");
    //nom jugadors
var nombre_p2 = document.querySelector("#j2");
var turno_p1= document.querySelector(".turn1");
var turno_p2= document.querySelector(".turn2");

//num col i files
const row_board = tablero.rows.length;
const col_board = tablero.rows[0].cells.length;
//Imatges interficie usuari
const img_j1 = "url(x.svg)";
const img_j2 = "url(circulo.svg)";
//Constants de modos de joc
const easy = 11;
const difficult = 12;
const hard = 13;
const multi= 14;
//Modo inicialitzat
var mode = easy;
//variable contador victories, empats
var score_p1=0;
var score_p2=0;
var score_ties=0;
//Boolean per bloquejar moviment jugador 1 quan es el torn del AI.
var movimiento=true;


/* #####################################################
    ONCLICK FUNCTIONS
    #################################################*/

for(var i=0;i<row_board;i++){
    for(var j=0;j<col_board;j++){
        
        tablero.rows[i].cells[j].onclick = function (){
            if(movimiento==true || mode==multi){
                movimiento=false;
                moviment(this);
            }
        };
    }
}

swap.onclick = function (){
    movimiento=true;
    swapMode();
    jugador = jugador1;
    turno_p2.style.display="none";
    turno_p1.style.display="block";
}
endgame.onclick = function(){
    movimiento=true;
    playAgain();
}

/* ##########################################################
    GAME FUNCTIONS
    ########################################################*/
function playAgain(){
    //interfaz.style.cursor = "default";
    endgame.style.cursor = "default";
    endgame.style.display = "none";
    reset();
}


function moviment(pos){
    if(gameOver){
        return;
   }
   
    var casella = pos.innerHTML.split(",");
    var row = casella[0];
    var col = casella[1];

    marcar(row,col,jugador);

    if(mode == multi && !gameOver){
        cambiar_jugador();
    }else if(mode!=multi){
        
        AI_turn(mode);
        
    }
    
   
    
   
}
function marcar(row,col,player){
    if(array[row][col] == 0){
        if (player == jugador1){
            
            tablero.rows[row].cells[col].style.backgroundImage= img_j1;
            array[row][col] = jugador1;
            turno_p1.style.display="none";
            turno_p2.style.display="block";
        }else{
            array[row][col] = jugador2;
            tablero.rows[row].cells[col].style.backgroundImage = img_j2
            turno_p2.style.display="none";
            turno_p1.style.display="block";
            movimiento=true;
            //tablero.rows[row].cells[col].style.backgroundImage = img_j2
           
            
        }
    }else{
        //evitar de que es canvii de torn en cas de que es cliqui una casella ja marcada
        marcar(row,col,player);
    }
    var guanyador = get_winner(row,col);
    if(guanyador==-1){
        if(checkTie(array)){
            guanyador = 0;
        }
    }
    if(guanyador!=-1){
        show_Winner(guanyador);  
    }

}


//Funcio per cambiar el torn a cada tirada
function cambiar_jugador(){
    if(jugador == jugador1){
        jugador = jugador2;
    }else{
        jugador = jugador1;
    }
}

//retorn el jugador guanyador, o -1 si no hi ha guanyador
function get_winner(row,col){
    if (columna_guanyadora(col)){
        return array[0][col];
    }
    else if(row_guanyadora(row)){
        return array[row][0];
    }
    else if(diag_guanyadora(row,col)){
        return array[1][1];

    }
    else{
        return -1;
    }
}

//comproba si no es poden fer més moviments i hi ha empat
function checkTie(array){
    var empat = true;
    for(var i=0;i<row_board;i++){
        for(var j=0;j<col_board;j++){
            if(array[i][j]==0){
                empat = false;
            }
        }
    }
    return empat;
}
//comproba si la columna que sha marcat hi ha 3 en ratlla
function columna_guanyadora(col){
    if(array[0][col]!=0 && array[0][col] == array[1][col] && array[0][col] == array[2][col]){
        return true;
    }else return false;
}
//comproba si la fila que s'ha marcat hi ha 3 en ratlla
function row_guanyadora(row){
    if(array[row][0]!=0 && array[row][0] == array[row][1] && array[row][0] == array[row][2]){
        return true;
    }else return false;
}
//comproba si la diganol que s'ha marcat hi ha 3 en ratlla
function diag_guanyadora(row,col){
    if((row==0&&col==0)||(row==1&&col==1)||(row==2&&col==2)){
        if(array[0][0]!=0 && array[0][0]==array[1][1]&&array[0][0]==array[2][2]){
            return true;
        }
    }
    if((row==0&&col==2)||(row==1&&col==1)||(row==2&&col==0)){
        if(array[0][2]!=0 && array[0][2]==array[1][1]&&array[0][2]==array[2][0]){
            return true;
        }
    }else return false;
}

/* ############################################################
                    AI FUNCTIONS
   ##########################################################*/

function AI_turn(mode){
    if(mode == easy){
        setTimeout(function(){easy_mode()},1000);
        //tablero.style.cursor="pointer";
        //easy_mode();
        
    } 
    else if(mode == difficult){
        setTimeout(function(){difficult_mode()},1000);
        //hard_mode_PAU();
    }
    else if(mode==hard){
        setTimeout(function(){hard_mode()},1000);
        //hard_mode();
    }
    

}
//robot plays random position, if random position is occupied searches for the first one empty
function easy_mode(){
    
    var acabat=false;
    if(gameOver){
        return;
    }
    var row = getRandomIntInclusive(0,2);
    var col = getRandomIntInclusive(0,2);
    
    if(array[row][col]==0){
        marcar(row,col,jugador2);
        acabat = true;
    }else{
        
        for(var i = 0;i<row_board && acabat==false;i++){
            for(var j=0;j<col_board && acabat==false;j++){
                if(array[i][j]==0 ){
                    marcar(i,j,jugador2);
                    acabat = true;
                } 
            }
        }
    }
}
var inici=0;
function difficult_mode(){
    if(gameOver){
        return;
    }
    
    var row_ultima=0;
    var col_ultima=0;
    var jugades=0;
    if(jugador==jugador1){
        for(var i=0;i<row_board;i++){
            for(var j=0;j<col_board;j++){
                if(array[i][j]==jugador1){
                    jugades=jugades+1;
                    row_ultima=i;
                    col_ultima=j;
                }
            }
        }
        if (jugades==1){
            if(row_ultima==0||row_ultima==2 && col_ultima==0 || col_ultima==2){
                marcar(1,1,jugador2);
                inici =1;
            
            }
            if(row_ultima==1 && col_ultima==1 ){
                marcar(0,0,jugador2);
                inici=2;
            }
            if(row_ultima==0&&col_ultima==1 ||row_ultima==1 && col_ultima==0 || row_ultima==1&& col_ultima==2 || row_ultima==2&& col_ultima==1){
                marcar(1,1,jugador2);
                inici=3;
            }
        }

        //tablero.style.backgroundColor = "purple";

        if(jugades!=1 ){
            var estar=false;
            estar=bloqueo(jugador2);
            if(estar==false){
                estar=bloqueo(jugador1);
            } 
            if(estar==false){
                //tablero.style.backgroundColor = "purple";
                buscar_ganar();

            }   
        }
    }
    if(jugador==jugador2){
        var estar=false;
        estar=bloqueo(jugador2);
        if(estar==false){
            estar=bloqueo(jugador1);
        } 
        if(estar==false){
            buscar_ganar();
        }   
    }
}
//funció de difficult_mode, aquesta busca la millor tirada, tant per guanyar com per impedir que guanyi el jugador
function bloqueo(persona){
    var j1;
    var j2;
    if(persona==jugador1){
        j1=1;
        j2=2;
    }
    else{
        j1=2;
        j2=2;
    }
    //Horizontales
    for(var i=0;i<row_board ;i++){
        for(var j=0;j<col_board;j++){
            if(array[i][j]==0 &&array[i][j+1]==j1 &&array[i][j+2]==j1){
                marcar(i,j,j2)
                return true;   
            }
            if(array[i][j]==j1 && array[i][j+1]==j1 && array[i][j+2]==0){
                marcar(i,j+2,j2);
                return true; 
            }
            if(array[i][j]==j1 &&array[i][j+1]==0 &&array[i][j+2]==j1){
                marcar(i,j+1,j2)
                return true
            }  
        }
    }
    //Verticales
    for(var i=0,j=0;j<col_board ;j++){
        if(array[i][j]==0 && array[i+1][j]==j1 && array[i+2][j]==j1){
            marcar(i,j,j2);
            return true;
        } 
        if(array[i][j]==j1 && array[i+1][j]==j1 && array[i+2][j]==0){
            marcar(i+2,j,j2);
            return true;
        }
        if(array[i][j]==j1 && array[i+1][j]==0 && array[i+2][j]==j1){
            marcar(i+1,j,j2);
            return true;
        }
    }
    //Diagonales
    for(var i=0,j=0;j<col_board ;j++){
        
        if(array[i][j]==0 &&array[i+1][j-1]==j1 &&array[i+2][j-2]==j1){
            marcar(i,j,j2)
            return true;
        }
        if(array[i][j]==j1 &&array[i+1][j-1]==j1 &&array[i+2][j-2]==0){
            marcar(i+2,j-2,j2)
            return true;
        }
        if(array[i][j]==0 &&array[i+1][j+1]==j1 &&array[i+2][j+2]==j1){
            marcar(i,j,j2)
            return true;
        }
        if(array[i][j]==j1 &&array[i+1][j+1]==j1 &&array[i+2][j+2]==0){
            marcar(i+2,j+2,j2)
            return true;
        }
    }
    return false;
}
//funcio per a difficult_mode, aquesta busca un el numero d'espais lliures, n'agafa un random i és on posarà la fitxa 
function buscar_ganar(){
    var forats_buits=espais_lliures(array).length; 
    var numero=getRandomIntInclusive(1,forats_buits);
    forats_buits=0;
    for(var i=0;i<row_board ;i++){
        for(var j=0;j<col_board;j++){
            if(array[i][j]==0){
                forats_buits=forats_buits+1;
                if(forats_buits==numero){
                     marcar(i,j,jugador2);
                    return true;
                }
            }
        }
    }
}   

function hard_mode(){
    if(gameOver){
        return;
    }
    var pos = findBestMove(array);
    var row = pos[0];
    var col = pos[1];
    marcar(row,col,jugador2);
}
function espais_lliures(newBoard){
    var indices = [];
    for(var i = 0; i<row_board;i++){
        for(var j = 0; j<col_board;j++){
            if(newBoard[i][j]==0){
                indices.push([i,j]);
            }
        }
    }
    return indices;
}
function findBestMove(newBoard){
    return minimax(newBoard,jugador2).index;
}

function minimax(newBoard, player){
    var espacios_libres = espais_lliures(newBoard);
    for(i=0;i<3;i++){
        for(j=0;j<3;j++){
            var winner = get_winner(i,j)
            if(winner!=-1){
                break;
            }
        }
        if(winner!=-1) break;
    }
    if(winner == jugador1){
        return {score:-10}
    }
    else if(winner == jugador2){
        return {score:10}
    }
    else if(espais_lliures(newBoard).length === 0){
        return {score: 0};
    }
    var moves = [];
    for(var i = 0; i < espacios_libres.length;i++){
        var move = {};
        move.index =  [espacios_libres[i][0],espacios_libres[i][1]];//newBoard[espacios_libres[i][0]][espacios_libres[i][1]]; //a mi no esta be.
        newBoard[espacios_libres[i][0]][espacios_libres[i][1]] = player;

        if(player == jugador2){
            var result = minimax(newBoard,jugador1);
            move.score = result.score;
        }else{
            var result = minimax(newBoard,jugador2);
            move.score = result.score;
        }

        newBoard[espacios_libres[i][0]][espacios_libres[i][1]] = 0;
        moves.push(move);
    }
    var bestMove;
    if(player==jugador2){
        var bestScore = -1000;
        for(var i = 0;i<moves.length;i++){
            if(moves[i].score > bestScore){
                bestScore = moves[i].score;
                bestMove = i;
            }
        }
    }
    if(player==jugador1){
        var bestScore = 1000;
        for(var i = 0;i<moves.length;i++){
            if(moves[i].score < bestScore){
                bestScore = moves[i].score;
                bestMove = i;
            }
        }
    }
    return moves[bestMove]
}

// Creates random variable between two numbers
function getRandomIntInclusive(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }




/* #######################################################
                HTML ELEMENTS FUNCTIONS
 #########################################################*/
function reset(){
    empty_board();
    if(jugador == jugador1){
        jugador =jugador2;
        turno_p2.style.display="block";
        turno_p1.style.display="none";
        if(mode!=multi){
            movimiento = false;
            jugador = jugador1;
            AI_turn(mode);
        }
    }else{
        jugador = jugador1;
        turno_p2.style.display="none";
        turno_p1.style.display="block";
    }
    

}
function empty_board(){
    array = [
        [0,0,0],
        [0,0,0],
        [0,0,0]
    ];
    gameOver = false;
    for(var i =0; i<row_board;i++){
        for(var j=0;j<col_board;j++){
            tablero.rows[i].cells[j].style.backgroundImage = '';
            tablero.style.backgroundColor = '';
        }
    }
}
//cambiar icone i mode de joc
function swapMode(){
    endgame.style.cursor = "default";
    endgame.style.display = "none";
    if(mode==easy){
        
        mode = difficult;
        display_easy.style.display = "none";
        display_difficult.style.display = "block";
        nombre_p2.innerHTML = " DIFFICULT AI"   
        
    }
    else if(mode==difficult){
        
        mode = hard;
        display_difficult.style.display = "none";
        display_hard.style.display = "block"; 
        nombre_p2.innerHTML = " UNBEATABLE AI" 
        
    }
    else if(mode==hard){
        
        mode = multi;
        display_hard.style.display = "none";
        display_multiplayer.style.display = "block";  
        nombre_p2.innerHTML = " JUGADOR 2" 
        
    }
    else if(mode==multi){
        
        mode = easy;
        display_multiplayer.style.display="none";
        display_easy.style.display="block";
        nombre_p2.innerHTML = " EASY AI";
        
    }
    score_p1= 0;
    scoreboard_1.innerHTML = score_p1;
    score_p2 = 0;
    scoreboard_2.innerHTML =score_p2;
    score_ties = 0;
    nTies.innerHTML = 0;
    empty_board();
}

function show_Winner(winner){
    //Impedim nous moviments
    gameOver = true;

    if(winner == jugador1){
        score_p1 += 1;
        scoreboard_1.innerHTML = score_p1;
        text_endgame.innerHTML = "JUGADOR 1 Wins!"
     
    }
    else if(winner == jugador2){
        score_p2 += 1;
        scoreboard_2.innerHTML = score_p2;
        switch(mode){
            case easy:
                text_endgame.innerHTML = "EASY AI Wins!"
                break;
            case difficult:
                text_endgame.innerHTML = "DIFFICULT AI Wins!"
                break;
            case hard:
                text_endgame.innerHTML = "UNBEATABLE AI Wins!"
                break;
            case multi:
                text_endgame.innerHTML = "JUGADOR 2 Wins!"
                break;
            }
        

    }
    else if(winner == 0){
        score_ties += 1;
        nTies.innerHTML = score_ties;
        text_endgame.innerHTML = "Draw!"

    }
    turno_p1.style.display = "none";
    turno_p2.style.display = "none";
    endgame.style.display = "block";
    endgame.style.cursor = "pointer";
}





