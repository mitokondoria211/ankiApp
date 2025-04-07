/**
 * 
 */

document.getElementById('deckImage').addEventListener('change', function (e) {
  const file = e.target.files[0];
  if (!file) return;

  // 元の画像サイズ（参考）
  const originalSizeMB = file.size / (1024 * 1024);

  new Compressor(file, {
    quality: 0.6,
    maxWidth: 800,
    success(result) {
      const compressedSizeMB = result.size / (1024 * 1024);
      if (compressedSizeMB > 5) {
        alert('圧縮後も5MBを超えているため、アップロードできません。');
        e.target.value = ''; // 選択をリセット
        return;
      }

      // 圧縮後のファイルをフォームデータに上書き（必要な場合）
      const dataTransfer = new DataTransfer();
      const compressedFile = new File([result], file.name, { type: result.type });
      dataTransfer.items.add(compressedFile);
      e.target.files = dataTransfer.files;
    },
    error(err) {
      console.error(err.message);
    }
  });
});