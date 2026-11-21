/**
 * BeautiPick Portal - Vanilla JavaScript
 * Handles landing page category filtering and multi-step quiz state.
 */

document.addEventListener('DOMContentLoaded', function () {
  initCategoryChips();
  initQuizCardSelection();
  initQuizProgress();
});

/* ==========================================================================
   1. Landing Page Category Filtering (item 14: show empty-category-msg)
   ========================================================================== */
function initCategoryChips() {
  var chipBar = document.getElementById('categoryChips');
  var grid = document.getElementById('productGrid');
  if (!chipBar || !grid) return;

  var chips = chipBar.querySelectorAll('.chip');
  var cards = grid.querySelectorAll('.product-card');
  var emptyMsg = document.getElementById('emptyCategoryMsg');

  chips.forEach(function (chip) {
    chip.addEventListener('click', function () {
      var filter = this.getAttribute('data-filter');

      // Update active chip state
      chips.forEach(function (c) { c.classList.remove('active'); });
      this.classList.add('active');

      // Filter product cards
      var visibleCount = 0;
      cards.forEach(function (card) {
        var cardCat = card.getAttribute('data-category');
        if (!filter || filter === 'All' || cardCat === filter) {
          card.style.display = 'flex';
          visibleCount++;
        } else {
          card.style.display = 'none';
        }
      });

      // item 14: toggle empty-category message
      if (emptyMsg) {
        emptyMsg.style.display = visibleCount === 0 ? 'block' : 'none';
      }
    });
  });
}

/* ==========================================================================
   2. Multi-step Quiz Engine
   ========================================================================== */
var currentStep = 1;
var totalSteps = 6;

function initQuizProgress() {
  var quizForm = document.getElementById('quizForm');
  if (!quizForm) return;

  var steps = quizForm.querySelectorAll('.quiz-step');
  totalSteps = steps.length;
  updateProgressUI();

  // Validate final step before form submission (item 20: inline alert)
  quizForm.addEventListener('submit', function (e) {
    var lastStepElem = quizForm.querySelector('.quiz-step[data-step="' + totalSteps + '"]');
    if (!lastStepElem) return;

    var inputs = lastStepElem.querySelectorAll('input.quiz-input');
    var hasSelection = false;
    inputs.forEach(function (input) {
      if (input.checked) {
        hasSelection = true;
      }
    });

    if (!hasSelection) {
      e.preventDefault();
      showQuizAlert(lastStepElem, 'Please select an option to complete the quiz.');
    }
  });
}

/* item 20: show inline alert message above Next button */
function showQuizAlert(stepElem, message) {
  var alertEl = stepElem.querySelector('.quiz-inline-alert');
  if (!alertEl) return;
  alertEl.textContent = message;
  alertEl.style.display = 'block';
  alertEl.focus();
}

function hideQuizAlert(stepElem) {
  var alertEl = stepElem && stepElem.querySelector('.quiz-inline-alert');
  if (alertEl) {
    alertEl.style.display = 'none';
  }
}

function updateProgressUI() {
  var stepCounter = document.getElementById('stepCounter');
  var stepPercent = document.getElementById('stepPercent');
  var progressBarFill = document.getElementById('progressBarFill');

  if (!stepCounter || !progressBarFill) return;

  var percent = Math.round((currentStep / totalSteps) * 100);
  stepCounter.textContent = 'Step ' + currentStep + ' of ' + totalSteps;
  if (stepPercent) {
    stepPercent.textContent = percent + '% Complete';
  }
  progressBarFill.style.width = percent + '%';
}

function goToNextStep() {
  var quizForm = document.getElementById('quizForm');
  if (!quizForm) return;

  var currentStepElem = quizForm.querySelector('.quiz-step[data-step="' + currentStep + '"]');
  if (!currentStepElem) return;

  // Validation: ensure at least one option is selected in current step
  var inputs = currentStepElem.querySelectorAll('input.quiz-input');
  var hasSelection = false;
  inputs.forEach(function (input) {
    if (input.checked) {
      hasSelection = true;
    }
  });

  if (!hasSelection) {
    // item 20: inline message instead of alert()
    showQuizAlert(currentStepElem, 'Please select an option to continue to the next step.');
    return;
  }

  // Clear any previous alert
  hideQuizAlert(currentStepElem);

  if (currentStep < totalSteps) {
    currentStepElem.classList.remove('active-step');
    currentStep++;
    var nextStepElem = quizForm.querySelector('.quiz-step[data-step="' + currentStep + '"]');
    if (nextStepElem) {
      nextStepElem.classList.add('active-step');
      updateProgressUI();
      // item 4: scroll smoothly to form (scroll-margin-top handles header offset)
      quizForm.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}

function goToPrevStep() {
  var quizForm = document.getElementById('quizForm');
  if (!quizForm) return;

  if (currentStep > 1) {
    var currentStepElem = quizForm.querySelector('.quiz-step[data-step="' + currentStep + '"]');
    if (currentStepElem) {
      hideQuizAlert(currentStepElem);
      currentStepElem.classList.remove('active-step');
    }
    currentStep--;
    var prevStepElem = quizForm.querySelector('.quiz-step[data-step="' + currentStep + '"]');
    if (prevStepElem) {
      prevStepElem.classList.add('active-step');
      updateProgressUI();
      // item 4: scroll-margin-top handles sticky header
      quizForm.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}

// Expose navigation functions to global scope for button onclick attributes
window.goToNextStep = goToNextStep;
window.goToPrevStep = goToPrevStep;

/* ==========================================================================
   3. Option Card Interaction Sync (Radio & Checkbox)
   ========================================================================== */
function initQuizCardSelection() {
  var quizForm = document.getElementById('quizForm');
  if (!quizForm) return;

  function syncCard(card) {
    if (!card) return;
    var input = card.querySelector('input.quiz-input');
    if (input && input.checked) {
      card.classList.add('selected');
    } else {
      card.classList.remove('selected');
    }
  }

  function syncGrid(grid) {
    if (!grid) return;
    var cards = grid.querySelectorAll('.option-card');
    cards.forEach(syncCard);
  }

  // Handle change event triggered natively by <label> clicking or keyboard
  quizForm.addEventListener('change', function (e) {
    var input = e.target;
    if (!input || !input.classList.contains('quiz-input')) return;

    var grid = input.closest('.options-grid');
    if (grid) {
      syncGrid(grid);
    }

    // Clear inline alert when user makes a selection
    var stepElem = input.closest('.quiz-step');
    hideQuizAlert(stepElem);
  });

  // Synchronize initial state on page load
  var allGrids = quizForm.querySelectorAll('.options-grid');
  allGrids.forEach(syncGrid);
}
