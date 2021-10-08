<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
           href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${codeSnippet.date}</span>

<#if (codeSnippet.viewsRestricted == true) && (codeSnippet.views >= 0)>
<span id="views_restriction">${codeSnippet.views} more views allowed</span>
</#if>
<#if (codeSnippet.timeRestricted == true) && (codeSnippet.time > 0)>
<span id="time_restriction">This code snippet will be available for ${codeSnippet.time} more seconds</span>
</#if>
<pre id="code_snippet"><code>${codeSnippet.code}</code></pre>

</body>
</html>