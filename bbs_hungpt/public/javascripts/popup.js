/**
 * Created by hung_pt on 7/31/17.
 */

var modal = document.getElementById('myModal');

modal.style.display = "block";
//
var span = document.getElementsByClassName("close")[0];
//
span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}