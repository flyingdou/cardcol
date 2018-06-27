<#assign itemCount = 0/>
<#if parameters.list?exists>
<ul class="check_list">
<@s.iterator value="parameters.list">
<li>
<#assign itemCount = itemCount + 1/>
<#if parameters.listKey?exists>
<#assign itemKey = stack.findValue(parameters.listKey)/>
<#else>
<#assign itemKey = stack.findValue('top')/>
</#if>
<#if parameters.listValue?exists>
<#assign itemValue = stack.findString(parameters.listValue)/>
<#else>
<#assign itemValue = stack.findString('top')/>
</#if>
<#assign itemKeyStr=itemKey.toString() />
<input type="checkbox" class="checkbox left" name="${parameters.name?html}" value="${itemKeyStr?html}" id="${parameters.name?html}_${itemCount}"<#rt/>
<#if tag.contains(parameters.nameValue, itemKey)>
checked="checked"<#rt/>
</#if>
<#if parameters.disabled?default(false)>
disabled="disabled"<#rt/>
</#if>
<#if parameters.title?exists>
title="${parameters.title?html}"<#rt/>
</#if>
<#include "/${parameters.templateDir}/simple/scripting-events.ftl" />
<#include "/${parameters.templateDir}/simple/common-attributes.ftl" />
/>
<label for="${parameters.name?html}_${itemCount}" class="checkboxLabel">${itemValue?html}</li>
<#if parameters.cssStyle?exists>
<#if "${parameters.cssStyle?html}" == "vertical">
<#if "${itemCount % 5}" == "0">
<#rt/>
</#if>
</#if>
</#if>
</@s.iterator>
<#else>
&nbsp;
</#if>
