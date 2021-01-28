# skenai-plugin
 
Sken.ai offers a DevOps-first Continuous Application Security Scanning. With Sken, your apps on Jenkins will be automatically scanned for security vulnerabilities. Sken is one tool to do all types of scans - SAST, DAST, SCA, Secrets and more, without the need for any AppSec expertise.

skenai-cli is a Jenkins plugin that allows the user to easily run Sken.ai's CLI.

First go to https://sken.ai and sign up for a free forever account. Then login to sken dashboard at https://dashboard.sken.ai , create a new application there and obtain your org-id and app-id for that application.

To add skenai-cli to the Jenkins project's build, select Build > Add build step > Sken.ai.

Configure skenai-cli by specifying your Organization ID (orgid) and Application ID (appid).

