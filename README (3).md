# Scientific Calculator (Java Swing)

A desktop scientific calculator built with **Java Swing**, supporting both basic arithmetic and common scientific functions, with an on-screen calculation history.

## Features

**Basic Operations**
- Addition (`+`), Subtraction (`-`), Multiplication (`×`), Division (`÷`)
- Modulus (`%`), Power (`^`)
- Backspace (`←`) and Clear (`C`)

**Scientific Functions**
- Trigonometric: `sin`, `cos`, `tan` (input in degrees)
- Logarithmic: `log` (base 10), `ln` (natural log)
- `√` (square root), `x²` (square), `1/x` (reciprocal), `!` (factorial)
- Constants: `π`, `e`
- Parentheses: `(` `)` (for display/reference)
- `Mode` button toggles the window title between Scientific and Basic

**Display**
- Main display shows the current number/result
- History label above it shows the last operation performed (e.g. `12 + 5`, `sin(30°) =`)

## Project Structure

```
ScientificCalculator.java   → Single-file Swing application
```

The whole app lives in one class, `ScientificCalculator`, which extends `JFrame`:

| Method | Responsibility |
|---|---|
| `createDisplayPanel()` | Builds the top display + history label |
| `createButtonPanel()` | Lays out all calculator buttons in a grid |
| `createButton(text)` | Styles an individual button based on its type (number / operator / scientific / action) |
| `handleButtonClick(text)` | Routes each button press to the right handler |
| `handleNumber`, `handleDecimal`, `handleOperator` | Core input logic |
| `handleTrigFunction`, `handleMathFunction` | Scientific calculations |
| `calculateResult()` | Evaluates the pending operator on `=` |
| `handleBackspace()`, `clearCalculator()` | Editing / reset |

## Requirements

- Java JDK 8 or later (uses only `javax.swing` and `java.awt`, no external libraries)

## How to Run

**Compile:**
```bash
javac ScientificCalculator.java
```

**Run:**
```bash
java ScientificCalculator
```

A calculator window (500×650) will open. Click buttons with the mouse to perform calculations.

## Notes / Known Limitations

- Trigonometric functions expect input in **degrees**, not radians.
- Parentheses (`(`, `)`) are inserted into the display but are not currently evaluated as part of expression precedence — the calculator works strictly step-by-step (number → operator → number → `=`), not full expression parsing.
- Factorial (`!`) only accepts non-negative whole numbers.
- Division by zero and invalid inputs (e.g. `log` of a non-positive number) show `"Error"` on the display.

## Possible Improvements

- Full expression parsing with proper operator precedence and working parentheses
- Keyboard input support
- Memory functions (M+, M-, MR, MC)
- Persistent calculation history list
- Radian/Degree toggle for trig functions
