(ns simple-calculator.system-interface
  #?(:clj
     (:import (CalculatorSystem SymbolType))))

(def numbers #{"0" "1" "2" "3" "4" "5" "6" "7" "8" "9"})
#?(:clj  (def operand-map {"+" SymbolType/addition
                           "-" SymbolType/subtraction
                           "*" SymbolType/multiplication
                           "/" SymbolType/division})
   :cljs (def operand-map {"+" js/Addition
                           "-" js/Subtraction
                           "*" js/Multiplication
                           "/" js/Division}))

(defn- get-type [character]
  (condp get character
    numbers :number
    (set (keys operand-map)) :operand
    #{"("} :bracket
    #{")"} :expr-end
    #{"^"} :power
    #{"x"} :variable))

(defn input-character [system character]
  (case (get-type character)
    :number (.inputNumber system #?(:clj  (Integer/parseInt character)
                                    :cljs (js/parseInt character)))
    :operand (.inputSymbol system (operand-map character))
    :bracket (.inputBrackets system)
    :variable (.inputX system)
    :expr-end (.inputEnd system)))