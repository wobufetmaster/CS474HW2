import MySetTheoryDSL.setExp.{Insert, Variable}

import scala.collection.mutable

object MySetTheoryDSL:
  type BasicType = Int
  private val set_rep = collection.mutable.Set()
  private val bindingScoping: Map[String, setExp] = Map("set1"->setExp.Value(5))
  private val scope_map: Map[(String,String), Set[Any]] = Map(("set1","scope1")->Set())
  private val current_scope: mutable.Stack[String] = new mutable.Stack[String]()
  enum setExp:
    case Value(input: BasicType)
    case Variable(name: String)
    case Macro(name: String)
    case Scope(name: String, op2:setExp)
    case Assign(name: Variable, op2: setExp)
    case Insert(name: Variable, op2: BasicType*)
    case Delete(name: Variable, op2: BasicType)
    case Union(op1: setExp, op2: setExp)
    case Intersection(op1: setExp, op2: setExp)
    case Difference(op1: setExp, op2: setExp)
    case SymmetricDifference(op1: setExp, op2: setExp)
    case Product(op1: setExp, op2: setExp)
    case NewSet()

    def eval(): Set[Any] = { //Walks through the AST and returns a set.
      this match {
        case Value(v) => Set(v)
        case Variable(name) => bindingScoping(name).eval()
        case Macro(a) => Set()
        case Scope(a,b) =>
          current_scope.push(a) //Push current scope onto stack
          val temp = b.eval() //Evaluate rhs
          current_scope.pop() //Current scope is over - go back to previous scope
          temp //Return the evaluated value
        case Assign(name, set) =>
          name match {
            case Variable(s) =>
              scope_map.updated((s,"scope1"),set.eval())
          }
          Set()
        case Insert(set_name, to_insert*) => set_name.eval() | to_insert.toSet


        case default => Set()
      }
    }

  def Check(set_name: String, set_val: setExp.Value): Boolean = {
    println(set_val.eval())
    println(bindingScoping(set_name).eval())
    bindingScoping(set_name).eval().subsetOf(set_val.eval())
  }

  @main def runSetExp(): Unit =
    import setExp.*

    //println("Hello world!")
    println(Assign(Variable("someSetName"), Insert(Variable("var"), 2,3,5,7)).eval())

    println(Insert(Variable("set1"),7,9).eval())
    println(Check("set1",Value(5)))
    //Scope("scopename", Scope("othername", Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value("somestring"))))

//println(Assign(Variable("ass"),Scope()))
    //val expression = Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value(1))
    //val expression2 = Assign(SetIdentity("someSetName"),Seq( Variable("var"), Value(1), Value(1)))
//val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    //println(firstExpression)
