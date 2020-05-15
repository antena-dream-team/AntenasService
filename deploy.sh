#!/bin/bash
ssh -t ec2-18-229-131-56.sa-east-1.compute.amazonaws.com \<<-'ENDSSH'
        ls
        pwd
        docker ps
        docker images
ENDSSH
