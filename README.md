# scramblies

FullStack Clojure + ClojureScript example.

## Overview

 Implements an API that accepts two strings and applies `scramble?` function. This function returns `true` if a portion of str1 characters can be rearranged to match str2, otherwise returns false.

 Scramble? function only accepts lower case letters [a-z]. No punctuation or digits.

 UI was created using Clojurescript + Reagent and Ajax.

## Setup

To get an interactive development environment run:

    npm install
    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

    lein run 8000

And open your browser in [localhost:8000](http://localhost:8000/). You will not
get live reloading, nor a REPL.

## License

Copyright © 2020

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
