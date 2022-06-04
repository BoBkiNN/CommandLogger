# CommandLogger
BungeeCord plugin for logging commands
## Features:
- Hidden commands (this commands will be not printed to console)
- Customizable log message
- Placholders: %server% for server name; %player% for sender nickname; %cmd% for command that was executed
## config.yml
```yaml
msg: '&a[%server%] %player% executed %cmd%'
reloadMsg: "&aConfig reloaded!"
hiddenCmds:
  - '/l '
  - '/log '
  - '/login '
  - '/reg'
  - '/changepass'
  - '/cp'
  - '/tell '
  - '/msg'
  - '/pm'
  - '/w '
  - '/pmsg'
  - '/whisper'
  - '/m'
```
