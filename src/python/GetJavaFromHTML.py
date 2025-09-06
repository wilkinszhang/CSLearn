#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import re
from bs4 import BeautifulSoup

def extract_java_code(html_path):
    with open(html_path, encoding='utf-8') as f:
        html = f.read()

    soup = BeautifulSoup(html, 'lxml')
    # 方法一：专门提取 class="se-f3c9ff56" 的代码行
    lines = soup.find_all('div', class_='se-f3c9ff56')
    code = '\n'.join(line.get_text() for line in lines if line.get_text().strip())
    if code.strip():
        return code

    # 方法二：回退到全局去标签
    return re.sub(r'<.*?>', '', html)

if __name__ == '__main__':
    java_code = extract_java_code('35.html')
    with open('output.java', 'w', encoding='utf-8') as out:
        out.write(java_code)
    print("提取完成，已保存至 output.java")
