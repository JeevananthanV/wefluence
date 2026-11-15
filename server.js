const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = process.env.PORT || 3002;

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.js': 'application/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.gif': 'image/gif',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon',
  '.woff': 'font/woff',
  '.woff2': 'font/woff2',
  '.ttf': 'font/ttf',
  '.pdf': 'application/pdf'
};

const server = http.createServer((req, res) => {
  const parsedUrl = new URL(req.url, `http://${req.headers.host || 'localhost'}`);
  let pathname = decodeURIComponent(parsedUrl.pathname);

  // If the route is /admin or /admin/*, serve admin.html
  if (pathname === '/admin' || pathname.startsWith('/admin/') || pathname === '/admin.html') {
    const adminPath = path.join(__dirname, 'admin.html');
    res.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8',
      'Cache-Control': 'no-cache'
    });
    fs.createReadStream(adminPath).pipe(res);
    return;
  }

  // Normalize path for general static serving
  let safePath = path.normalize(pathname).replace(/^(\.\.[\/\\])+/, '');
  let filePath = path.join(__dirname, safePath);

  // If path is root or empty, serve root index.html
  if (pathname === '/' || pathname === '') {
    filePath = path.join(__dirname, 'index.html');
  }

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      // If it's a request for an asset with an extension (e.g. .css, .js, .png) that doesn't exist, return 404
      if (path.extname(pathname)) {
        res.writeHead(404, { 'Content-Type': 'text/plain; charset=utf-8' });
        res.end('Not Found');
        return;
      }
      // SPA Fallback for client-side routing (e.g. /auth, /dashboard, /campaigns, /profile)
      filePath = path.join(__dirname, 'index.html');
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';

    res.writeHead(200, {
      'Content-Type': contentType,
      'Cache-Control': ext === '.html' ? 'no-cache' : 'public, max-age=31536000, immutable'
    });

    const stream = fs.createReadStream(filePath);
    stream.on('error', () => {
      if (!res.headersSent) {
        res.writeHead(500, { 'Content-Type': 'text/plain; charset=utf-8' });
      }
      res.end('Server Error');
    });
    stream.pipe(res);
  });
});

server.listen(PORT, () => {
  console.log(`====================================================`);
  console.log(`🚀 WeFluence Platform Server is running!`);
  console.log(`🔗 Public Site: http://localhost:${PORT}`);
  console.log(`⚡ Admin Panel: http://localhost:${PORT}/admin`);
  console.log(`✨ Mode:        Production SPA + Admin Command Center`);
  console.log(`====================================================`);
});
