function updateTime() {
  const now = new Date();
  const timeString = now.toLocaleString('ja-JP', { hour: '2-digit', minute: '2-digit', second: '2-digit' });
  document.getElementById('real-time').textContent = timeString;
}

setInterval(updateTime, 1000);
updateTime();