<#-- common constants, functions and methods for tuples -->

<#macro AutoGeneratedWarning>
// AUTOGENERATED CODE.  DO NOT MODIFY DIRECTLY!  Instead, please modify the ${.caller_template_name} file.
// See the README in the module's src/template directory for details.
</#macro>

<#assign typeParameters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"] />

<#assign maxTupleSize = 20 />

<#macro TupleValue index>
  <@compress single_line=true>
    TupleValue${index}<${typeParameters[index]}>
  </@compress>
</#macro>

<#-- Gets the fully-parameterized tuple type -->
<#macro Tuple arity>
  <@compress single_line=true>
    Tuple${arity}<<#list 0..<arity as index>${typeParameters[index]}<#sep>, </#list>>
  </@compress>
</#macro>

<#-- Gets the fully-parameterized tuple type with one of the parameterized replaced by a different type -->
<#macro TupleWithNewType arity genericIndex type='Z'>
  <@compress single_line=true>
    Tuple${arity}<<#list 0..<arity as index><#if genericIndex == index>${type}<#else>${typeParameters[index]}</#if><#sep>, </#list>>
  </@compress>
</#macro>

<#macro ArrayTuple arity>
  <@compress single_line=true>
    ArrayTuple${arity}<<#list 0..<arity as index>${typeParameters[index]}<#sep>, </#list>>
  </@compress>
</#macro>

<#macro FieldTuple arity>
  <@compress single_line=true>
    FieldTuple${arity}<<#list 0..<arity as index>${typeParameters[index]}<#sep>, </#list>>
  </@compress>
</#macro>

<#macro TypeParameters arity>
  <@compress single_line=true>
    <#list 0..<arity as index>${t.typeParameters[index]}<#sep>, </#list>
  </@compress>
</#macro>