function checkPassword() {
  const pw1 = document.getElementById("password1").value;
  const pw2 = document.getElementById("password2").value;

  if (pw1 !== pw2) {
    alert("비밀번호가 달라요! 다시 확인해주세요 🦁");
    return false;
  }
  return true;
}
