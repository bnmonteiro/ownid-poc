function IframeContent() {
  const token = getTokenFromCookie();

  function getTokenFromCookie() {
    const match = document.cookie.match(new RegExp("(^| )token=([^;]+)"));
    return match ? match[2] : null;
  }

  function deleteTokenCookie() {
    document.cookie =
      "token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC; SameSite=Lax";
  }

  function logout() {
    deleteTokenCookie();
    window.location.href = "/login";
  }

  return (
    <div>
      <h2>IframeContent Page</h2>

      <h3>User token from cookies: {token}</h3>

      <button onClick={logout}>Logout</button>
    </div>
  );
}

export default IframeContent;
