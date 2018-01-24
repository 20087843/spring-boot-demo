# Maven

## introduce

## install

## description
  1. [pom.xml example](pom-explain.xml)
  
  2. dependency scope explain
  
  | name | explain |
  |------|------|
  |   compile（default）   |   适用于所有阶段，会随着项目一起发布  |
  |   provided   |   类似compile，期望jdk、容器或使用者会提供这个依赖   |
  |   runtime   |    只在运行时使用，如jdbc驱动，适用运行和测试阶段  |
  |   test   |   只在测试时使用，用于编译和运行测试代码。不会随项目发布   |
  |   system   |   类似provided，需要显式提供包含依赖的jar，maven不会在repository中查找它   |
  
  3. scope 依赖传递
  
  |      |	compile | provided | runtime | test |
  |------|------|------|------|------|
  |   compile   |   compile(*)   |   -   |   runtime   |   -   |
  |   provided   |   provided   |   -   |   provided   |   -   |
  |   runtime   |   runtime   |   -   |   runtime   |   -   |
  |   test   |   test   |   -   |   test   |   -   |

