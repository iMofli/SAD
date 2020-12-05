var array = [
    [0,0,0],
    [0,0,0],
    [0,0,0]
];

const jugador1 = 1;
const jugador2 = 2;
var jugador= jugador1;

var gameOver=false;

var tablero = document.getElementById("tablero");
const row_board = tablero.rows.length;
const col_board = tablero.rows[0].cells.length;
const img_j1 = "url(x.png)";
const img_j2 = "url(circle.png)";






for(var i=0;i<row_board;i++){
    for(var j=0;j<col_board;j++){
        tablero.rows[i].cells[j].onclick = function (){
          
            moviment(this);   
        };
    }
}


function moviment(pos){

    if(gameOver){
        tablero.style.backgroundColor = "purple";
        return;
   }
   
    var casella = pos.innerHTML.split(",");
    var row = casella[0];
    var col = casella[1];

    if(array[row][col] == 0){
        if (jugador == jugador1){
            pos.style.backgroundImage= img_j1;   
            array[row][col] = jugador1;
        }else{
            pos.style.backgroundImage = img_j2;
            array[row][col] = jugador2;
        }
    }
    var guanyador = get_winner2(row,col);
    cambiar_jugador();
    if(guanyador!=-1){
        print("ghl");
        gameOver = true;
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

function get_winner2(row,col){
    if (columna_guanyadora(col)){
        return array[0][col];
    }
    else if(row_guanyadora(row)){
        return array[row][0];
    }
    else if(diag_guanyadora(row,col)){
        return array[1][1];
    }else{
        return -1;
    }
}
function columna_guanyadora(col){
    if(array[0][col]!=0 && array[0][col] == array[1][col] && array[0][col] == array[2][col]){
        return true;
    }else return false;
}
function row_guanyadora(row){
    if(array[row][0]!=0 && array[row][0] == array[row][1] && array[row][0] == array[row][2]){
        return true;
    }else return false;
}
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

function reset(){
    array = [
        [0,0,0],
        [0,0,0],
        [0,0,0]
    ];
    gameOver = false;
    for(var i =0; row_board;i++){
        for(var j=0;j<col_board;j++){
            tablero.rows[i].cells[j].style.backgroundImage = '';
            tablero.style.backgroundColor = '';
        }
    }   
}
