# CommandLogger
BungeeCord plugin for logging commands
## Features:
- Hidden commands (this commands will be not printed to console)
- possible to ignore command case or not
- Customizable log message
- Placholders: %server% for server name; %player% for sender nickname; %cmd% for command that was executed
- Option to log messages with plugin preifix or without it
## Commands and permissions:
- `clreload` - reloads config, permission: `commandlogger.reload`
## config.yml
```yaml
msg: '&a[%server%] %player% executed %cmd%'
hiddenCmds:
  - '/l '
  - '/log '
  - '/login'
  - /reg
  - /changepass
  - /cp
  - '/tell '
  - '/msg '
  - /pm
  - /pmsg
  - '/w '
  - /m
  - /whisper
  - /LOGIN
  - '/r '
ignoreCase: true
reloadMsg: "&aConfig reloaded!"
addPrefix: false
```
