import MySetTheoryDSL.setExp.Variable

object MySetTheoryDSL:
  type BasicType = Int

  enum setExp:
    case Value(input: BasicType)
    case Variable(name: String)
    case Check(op1: setExp, op2: BasicType)
    case Macro(name: String)
    case Scope()
    case Assign(name: Variable, op2: Seq[setExp])
    case Insert(name: Variable, op2: BasicType)
    case Delete(name: Variable, op2: BasicType)
    case Union(op1: setExp, op2: setExp)
    case Intersection(op1: setExp, op2: setExp)
    case Difference(op1: setExp, op2: setExp)
    case SymmetricDifference(op1: setExp, op2: setExp)
    case Product(op1: setExp, op2: setExp)
    def check: Boolean = {false}

  @main def runSetExp(): Unit =
    import setExp.*
    println("Hello world!")
    //val expression = Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value(1))
    //val expression2 = Assign(SetIdentity("someSetName"),Seq( Variable("var"), Value(1), Value(1)))
//val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    //println(firstExpression)
