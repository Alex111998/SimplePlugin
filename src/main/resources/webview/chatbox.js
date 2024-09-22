function changeTheme(isDark) {
    const highlightCss = document.getElementById('highlightCss');
    const chatboxCss = document.getElementById('chatboxCss');
    if (isDark) {
        highlightCss.href = 'styles/nnfx-dark.min.css';
        chatboxCss.href = 'styles/chatbox.dark.css';
    } else {
        highlightCss.href = 'styles/nnfx-light.min.css';
        chatboxCss.href = 'styles/chatbox.light.css';
    }
}

function createQuestion(question) {
    if (!question) {
        question = '來一段java代码';
    }
    const userMessageDiv = document.createElement('div');
    userMessageDiv.classList.add('message', 'user-message');
    userMessageDiv.textContent = question;
    chatBox.appendChild(userMessageDiv);
}

function createAnswer(answer) {
    if (answer) {
        answer = atob(answer);
    } else {
        answer = codeContent;
    }
    // 模拟回复 Java 代码，逐个字符输出
    const botMessageDiv = document.createElement('div');
    botMessageDiv.classList.add('message', 'bot-message');
    chatBox.appendChild(botMessageDiv);
    let replyText = '回复：';
    appendContent(botMessageDiv, replyText, true, () => {
        // start code block
        const codeDiv = document.createElement('div');
        codeDiv.classList.add('code-div');

        const codeBlock = document.createElement('pre');
        const code = document.createElement('code');
        code.classList.add('java');
        codeBlock.appendChild(code);
        codeDiv.appendChild(codeBlock);
        botMessageDiv.appendChild(codeDiv);

        appendContent(code, answer, true, () => {
            // 添加复制按钮
            const copyButton = document.createElement('button');
            copyButton.classList.add('copy-button');
            copyButton.title = '复制';
            copyButton.innerHTML = '📋';
            codeDiv.appendChild(copyButton);
        });
        // end code block
    });
}

function appendContent(element, content, realtime, func) {
    if (!realtime) {
        element.textContent = content;
        return;
    }
    let index = 0;
    const intervalId = setInterval(() => {
        if (index < content.length) {
            element.textContent += content[index];
            index++;
            hljs.highlightAll();
            // 自动滚动到最后一行
            chatBox.scrollTop = chatBox.scrollHeight;
        } else {
            clearInterval(intervalId);
            if (func) {
                func();
            }
        }
    }, 10);
//    append(0, element, content);
//    if (func) {
//        func();
//    }
}

function append(index, element, content){
    if (index < content.length) {
        element.textContent += content[index];
        hljs.highlightAll();
        // 自动滚动到最后一行
        chatBox.scrollTop = chatBox.scrollHeight;
        setTimeout("append("+ (++index) + ",'" + element + "','" + content +"')", 1000)
    }
}

let codeContent = `public class Man {
    /**
     * 名稱
     */
    public static final String NAME = "Human";

    public static void main(String[] args) {
        System.out.println(String.format("Name: %s", NAME));
    }
}`;

let socket;
function connectWebSocket(port) {
    socket = new WebSocket("ws://localhost:" + port + "/socket");
    // 当连接建立时
    socket.onopen = event => {
        console.log("WebSocket connection established.");
        createQuestion("WebSocket connection established. Port is " + port);
    }

    // 当收到消息时
    socket.onmessage = event => {
        console.log("Message from server ", event.data);
    }

    // 当连接关闭时
    socket.onclose = event => {
        console.log("WebSocket connection closed: " + event.code + " " + event.reason);
    }

    // 当发生错误时
    socket.onerror = error => {
        console.error("WebSocket error: " + error);
    }
}

// 发送消息
function sendMessage(message) {
    if (message) {
        socket.send(message);
    }
}

function close() {
    // 关闭 WebSocket 连接
    socket.close();
    // 关闭 WebSocket 连接并指定状态码和原因
    socket.close(1000, "Connection closed by user");
}