const API = "";
let score = 0;
let gameOver = false;

function startGame() {
  document.getElementById("letter").disabled = false;
  document.getElementById("guessBtn").disabled = false;
  gameOver = false;
  resetStickman();

  const cat = document.getElementById("category").value;
  const diff = document.getElementById("difficulty").value;

  fetch(`/start?category=${cat}&difficulty=${diff}`, {
    method: "POST"
  })
    .then(res => res.json())
    .then(data => update(data))
    .catch(() => alert("Backend not reachable"));
}

function guess() {
  if (gameOver) return;

  let letter = document.getElementById("letter").value.toLowerCase();

  if (!letter) {
    alert("Please enter a letter");
    return;
  }

  if (!/^[a-z]$/.test(letter)) {
    alert("Enter a single letter (a-z)");
    return;
  }

  fetch(`/guess/${letter}`, {
    method: "POST"
  })
    .then(res => res.json())
    .then(data => update(data))
    .catch(() => alert("Error guessing letter"));

  document.getElementById("letter").value = "";
}

function update(data) {

  // Display word
  const wordContainer = document.getElementById("word");
  wordContainer.innerHTML = "";

  data.word.split(" ").forEach(word => {
    const wordDiv = document.createElement("div");
    wordDiv.className = "word";

    word.split("").forEach(letter => {
      const span = document.createElement("span");
      span.className = "letter";
      span.innerText =
        data.guessed && data.guessed.includes(letter)
          ? letter
          : "_";
      wordDiv.appendChild(span);
    });

    wordContainer.appendChild(wordDiv);
  });

  // Score
  score = data.score;
  document.getElementById("score").innerText = "Score: " + score;

  // Attempts
  document.getElementById("attempts").innerText =
    "Attempts left: " + data.attempts + " / " + data.maxAttempts;

  // Stickman
  const wrong = data.maxAttempts - data.attempts;
  updateStickman(wrong);

  // Lose
  if (data.attempts <= 0 && !gameOver) {
    gameOver = true;
    alert("Game Over 😢");
    document.getElementById("letter").disabled = true;
    document.getElementById("guessBtn").disabled = true;
    return;
  }

  // Win
  if (
    !gameOver &&
    !data.word.split("").some(
      l => l !== " " && !(data.guessed && data.guessed.includes(l))
    )
  ) {
    gameOver = true;
    alert("You Win 🎉");
    document.getElementById("letter").disabled = true;
    document.getElementById("guessBtn").disabled = true;
  }

} 

function updateStickman(wrong) {
  const parts = document.querySelectorAll(
    ".head, .body, .arm.left, .arm.right, .leg.left, .leg.right"
  );

  parts.forEach((part, index) => {
    if (index < wrong) {
      part.classList.remove("hidden");
    } else {
      part.classList.add("hidden");
    }
  });
}

function resetStickman() {
  document
    .querySelectorAll(
      ".head, .body, .arm.left, .arm.right, .leg.left, .leg.right"
    )
    .forEach(part => part.classList.add("hidden"));
}