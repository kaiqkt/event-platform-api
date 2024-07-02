#!/bin/sh

echo "Creating SQS Queues"

awslocal sqs create-queue --queue-name event-platform

echo "Listing SQS Queues"

awslocal sqs list-queues
