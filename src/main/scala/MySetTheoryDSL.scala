object MySetTheoryDSL:
  type BasicType = Int

  enum setExp:
    case Value(input: BasicType)
    case Variable(name: String)
    case Check(op1: setExp, op2: BasicType)
    case Macro(name: String)
    case Scope()
    case Assign(name: String, op2: setExp)
    case Insert(name: String, op2: BasicType)
    case Delete(name: String, op2: BasicType)
    case Union(op1: setExp, op2: setExp)
    case Intersection(op1: setExp, op2: setExp)
    case Difference(op1: setExp, op2: setExp)
    case SymmetricDifference(op1: setExp, op2: setExp)
    case Product(op1: setExp, op2: setExp)

    private val bindingScoping: Map[String, BasicType] = Map("x"->2, "Adan"->10)

    def eval: BasicType = {
      this match
        case default => 0
    }

  @main def runSetExp(): Unit =
    import setExp.*
    println("Hello world!")
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    //println(firstExpression)
