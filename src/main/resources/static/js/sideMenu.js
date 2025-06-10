// サイドメニューの開閉状態を追跡する変数
let menuOpen = false;

// サイドメニューの開閉を切り替える関数
function toggleSideMenu() {
  const sideMenu = document.getElementById('sideMenu');
  const menuIcon = document.getElementById('menuIcon');
  const body = document.body;
  
  // メニューの開閉状態を切り替え
  menuOpen = !menuOpen;
  
  if (menuOpen) {
    // メニューを開く
    sideMenu.classList.add('open');
    body.classList.add('menu-open');
    // アイコンをXに変更
    menuIcon.setAttribute('data-lucide', 'x');
  } else {
    // メニューを閉じる
    sideMenu.classList.remove('open');
    body.classList.remove('menu-open');
    // アイコンをメニューに戻す
    menuIcon.setAttribute('data-lucide', 'menu');
  }
  
  // Lucideアイコンを更新
  lucide.createIcons();
}

// ページ読み込み完了時に実行
document.addEventListener('DOMContentLoaded', function() {
  // 初期状態設定（ウィンドウサイズによる）
  const mediaQuery = window.matchMedia('(max-width: 768px)');
  
  function handleScreenChange(e) {
    if (!e.matches) {
      // 大きい画面サイズになった場合、メニューを開いた状態にリセット
      const sideMenu = document.getElementById('sideMenu');
      const menuIcon = document.getElementById('menuIcon');
      
      sideMenu.classList.remove('open');
      document.body.classList.remove('menu-open');
      menuIcon.setAttribute('data-lucide', 'menu');
      menuOpen = false;
      
      // アイコン更新
      lucide.createIcons();
    }
  }
  
  // 初期チェックとリスナー登録
  handleScreenChange(mediaQuery);
  mediaQuery.addEventListener('change', handleScreenChange);
});