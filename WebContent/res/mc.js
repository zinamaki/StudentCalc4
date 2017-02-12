/**
 * This javascript file is used to validate the formd data
 */

function validate() {
	var ok = true;
	var p = document.getElementById("principal").value;
	if (isNaN(p) || p <= 0) {
		alert("Principal invalid!");
		ok = false;
	}
	if (!ok)
		return false;
	p = document.getElementById("interest").value;
	if (isNaN(p) || p <= 0 || p >= 100) {
		alert("Interest invalid. Must be in [0,100].");
		ok = false;
	}
	return ok;
}