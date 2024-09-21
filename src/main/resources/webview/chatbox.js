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

function createQuestion() {
    const userMessageDiv = document.createElement('div');
    userMessageDiv.classList.add('message', 'user-message');
    userMessageDiv.textContent = '你：來一段java代碼';
    chatBox.appendChild(userMessageDiv);
}

function createAnswer() {
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

        appendContent(code, codeContent, true, () => {
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