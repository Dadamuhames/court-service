#!/bin/bash

/bin/ollama serve &

pid=$!

sleep 5

ollama pull nomic-embed-text

wait $pid
