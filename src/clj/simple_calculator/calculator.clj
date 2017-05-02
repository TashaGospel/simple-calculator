(ns simple-calculator.calculator
  (:import (CalculatorSystem Calculator SymbolType)))

(def numbers #{"0" "1" "2" "3" "4" "5" "6" "7" "8" "9"})
(def operand-map {"+" SymbolType/addition
                  "-" SymbolType/subtraction
                  "*" SymbolType/multiplication
                  "/" SymbolType/division})

(defn- get-type [character]
  (condp get character
    numbers :number
    (set (keys operand-map)) :operand
    #{"("} :bracket
    #{")"} :expr-end
    #{"^"} :power
    #{"x"} :variable))

(defn calculate [string]
  (let [calculator (Calculator.)]
    (doseq [character string]
      (let [character (str character)]
        (case (get-type character)
          :number (.inputNumber calculator (Integer/parseInt character))
          :operand (.inputSymbol calculator (operand-map character))
          :bracket (.inputBrackets calculator)
          :variable (.inputX calculator)
          :expr-end (.inputEnd calculator))))
    (.simplify calculator)
    (str calculator)))

