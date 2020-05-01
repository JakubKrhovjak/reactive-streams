#!/usr/bin/env bash
rm -rf ../reactive-producer/src/main/resources/static
mv build/static ../reactive-producer/src/main/resources/static
mv build/* ../reactive-producer/src/main/resources/
