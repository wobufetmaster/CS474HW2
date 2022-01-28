object MySetTheoryDSL:
  type BasicType = Int

  enum setExp:
    case Value(input: BasicType)
    case Var(name: String)
    case Add(op1: setExp, op2: setExp)
    case Sub(op1: setExp, op2: setExp)
    private val bindingScoping: Map[String, Int] = Map("x"->2, "Adan"->10)
    def eval: BasicType = {1}

  @main def runSetExp(): Unit =
    import setExp.*
    println("Hello world!")
    val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println(firstExpression)
