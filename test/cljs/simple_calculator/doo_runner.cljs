(ns simple-calculator.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [simple-calculator.core-test]))

(doo-tests 'simple-calculator.core-test)

