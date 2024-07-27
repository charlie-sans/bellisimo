
# Bellisimo
### The rainbow bridge offical funny

A working yet teribly made CC: Tweaked mod for the [Rainbow Bridge Server](https://discord.gg/9dRMpRbK)

Feel free to use this mod on your own server or modpack but this mod is developed for the server and may not work properly on other servers

# Table of Contents
1. [Chat Peripheral](#Chat-Peripheral)
2. [Bellisimo Command](#Bellisimo-Command)

# Chat Peripheral
> If you want to recieve chat messages from a turtle you need to put the peripheral in the world like the turtle is a regular computer. This will be fixed later

The chat peripheral lets you send and recieve chat messages

| Function | Description |
| ----------- | ----------- |
| open() | Opens the peripheral for listening for messages |
| close() | Closes the peripheral and stops listening |
| send(string msg) | Sends a message |

When open computer will start recieving a `chat_message` event that returns the message and the person who sent the message.<br>
Whenever another computer sends a message the sender would be `Computer <id>`. You also do not get a `chat_message` event for messages sent from the same computer

Example
```lua
-- DO NOT RUN THIS ON MANY COMPUTERS doing so will cause both computer to respond to eachother and not be good
local chat = peripheral.find("chat_peripheral") -- get a reference to the chat_peripheral
chat.open() -- start listening for chat messages
while true do
    local _, message, sender = os.pullEvent("chat_message") -- recieve the chat message event
    chat.send(sender .. " sent " .. message)
end
```

This will send a message in chat whenever someone sends a message 

# Bellisimo Command

This mod adds a `/bellisimo` command
<br>
this command has two sub command. `say` and `stop`

## Say
`/bellisimo say` takes two arguments, a id and a message. This will send a message that only the givin computer can see

## Stop 

`/bellisimo stop` takes one optional argument and is op only and lets you prevent a computer from sending messages

This is a toggle. If you run `/bellisimo stop` on a computer twice then that computer can send messages again

 if you provide no arguments then it stops every computer from sending messages. If this is on then no computer can send a message. Even if you run `/bellisimo stop` on a computer that computer cannot send messages until you run `/bellisimo stop` with no arguments again.