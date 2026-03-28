function checkEdit() {
  const savedId = localStorage.getItem("userId");
  const inputId = prompt("아이디를 입력하세요.");

  if (inputId === savedId) {
    alert("✅ 수정 권한이 확인되었습니다!");
  } else {
    alert("❌ 수정 권한이 없습니다!");
  }
}