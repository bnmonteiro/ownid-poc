function Account() {
  return (
    <iframe
      // iframe https used to test on safari browser
      // src="https://02bb-2804-7f0-bd41-6e3-4d6a-c883-8017-5e5d.ngrok-free.app/iframe/"
      src="http://localhost:3000/iframe/"
      title="Princess test"
      height="800"
      width="500"
    ></iframe>
  );
}

export default Account;
