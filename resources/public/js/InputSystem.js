const Addition = 1;
const Subtraction = 2;
const Multiplication = 3;
const Division = 4;

const NumberNode = 1;
const RootNode = 2;
const PowerNode = 3;
const LogNode = 4;
const BracketsNode = 5;
const FractionNode = 6;
const FactorialNode = 7;
// const SinNode = 8;
// const CosNode = 9;
// const TanNode = 10;
const GcdNode = 11;
const LcmNode = 12;
const CombinationNode = 13;
const PermutationNode = 14;
const PiNode = 15;
const ENode = 16;
const XNode = 17;
const EquationNode = 18;

function Node(nodeType, son1, son2) {
	this.head = "";
  this.displayHead = false;
	this.nodeType = nodeType;
	this.son1 = son1;
	this.son2 = son2;
	this.toString = function() {
		var ret = "";
    if (this.displayHead) {
      ret += this.head;
    }
		switch(this.nodeType) {
			case NumberNode: break;
      case RootNode: ret += "color(black)()^[" + this.son1.toString() + "]sqrt[" + this.son2.toString() + "]"; break;
      case PowerNode: ret += this.son1.toString() + "^[" + this.son2.toString() + "]"; break;
      case LogNode: ret += "log_[" + this.son1.toString() + "]" + this.son2.toString(); break;
      case BracketsNode: ret += "(" + this.son1.toString() + ")"; break;
      case FractionNode: ret += "frac[" + this.son1.toString() + "][" + this.son2.toString() + "]"; break;
      case FactorialNode: ret += "\"!\""; break;
      case GcdNode: ret += "Gcd[" + this.son1.toString() + "][" + this.son2.toString() + "]"; break;
      case LcmNode: ret += "Lcm[" + this.son1.toString() + "][" + this.son2.toString() + "]"; break;
      case CombinationNode: ret += "color(black)()^[" + this.son1.toString() + "]nCr_[" + this.son2.toString() + "]"; break;
      case PermutationNode: ret += "color(black)()^[" + this.son1.toString() + "]nPr_[" + this.son2.toString() + "]"; break;
      case PiNode: ret += "pi"; break;
      case ENode: ret += "e"; break;
      case XNode: ret += "x"; break;
      case EquationNode: ret += this.son1.toString() + "=" + this.son2.toString(); break;
			default: break;
		}
		return ret;
	}
}
function Block() {
	this.nodes = [new Node(NumberNode, null, null)];
	this.symbolTypes = [Multiplication];
  this.displaySymbol = [false];
	this.toString = function() {
		var ret = "";
		for (var i = 0; i < this.nodes.length; ++i) {
      if (this.displaySymbol[i]) {
        if (this.symbolTypes[i] == Multiplication) ret += "*";
        else ret += "/";
      }
			ret += this.nodes[i].toString();
		}
		return ret;
	}
}
function Polynomial() {
	this.blocks = [new Block()];
	this.symbolTypes = [Addition];
  this.displaySymbol = [false];
	this.toString = function() {
		var ret = "";
		for (var i = 0; i < this.blocks.length; ++i) {
      if (this.displaySymbol[i]) {
        if (this.symbolTypes[i] == Addition) ret += "+";
        else ret += "-";
      }
			ret += this.blocks[i].toString();
		}
		return ret;
	}
}
function InputSystem() {
	this.polynomial = new Polynomial();
	this.inputPolynomial = this.polynomial;
	this.inputBlock = this.polynomial.blocks[0];
	this.inputNode = this.inputBlock.nodes[0];
	this.fatherPolynomial = [];
	this.fatherBlock = [];
	this.fatherNode = [];

	this.pushStack = function() {
		this.fatherPolynomial.push(this.inputPolynomial);
		this.fatherBlock.push(this.inputBlock);
		this.fatherNode.push(this.inputNode);
		this.inputPolynomial = this.inputNode.son1;
		this.inputBlock = this.inputPolynomial.blocks[0];
		this.inputNode = this.inputBlock.nodes[0];
	}
	this.inputNumber = function(number) {
    this.inputNode.head += number.toString();
    this.inputNode.displayHead = true;
	}
  this.inputPoint = function() {
    this.inputNode.head += ".";
    this.inputNode.displayHead = true;
  }
	this.inputSymbol = function(symbolType) {
		if (symbolType == Addition || symbolType == Subtraction) {
			this.inputPolynomial.blocks.push(new Block());
			this.inputPolynomial.symbolTypes.push(symbolType);
      this.inputPolynomial.displaySymbol.push(true);
			this.inputBlock = this.inputPolynomial.blocks[this.inputPolynomial.blocks.length - 1];
			this.inputNode = this.inputBlock.nodes[0];
		}
		else {
			this.inputBlock.nodes.push(new Node(NumberNode, null, null));
			this.inputBlock.symbolTypes.push(symbolType);
      this.inputBlock.displaySymbol.push(true);
			this.inputNode = this.inputBlock.nodes[this.inputBlock.nodes.length - 1];
		}
	}
  this.inputZeroSon = function(nodeType) {
    if (!this.inputNode.displayHead) this.inputNode.head = "1";
    this.inputNode.nodeType = nodeType;
  }
  this.inputOneSon = function(nodeType) {
    if (!this.inputNode.displayHead) this.inputNode.head = "1";
    this.inputNode.nodeType = nodeType;
    this.inputNode.son1 = new Polynomial();
    this.pushStack();
  }
  this.inputTwoSons = function(nodeType) {
    if (!this.inputNode.displayHead) this.inputNode.head = "1";
    this.inputNode.nodeType = nodeType;
    this.inputNode.son1 = new Polynomial();
    this.inputNode.son2 = new Polynomial();
    this.pushStack();
  }
  this.inputRoot = function() {
    this.inputTwoSons(RootNode);
  }
  this.inputPower = function() {
    this.inputTwoSons(PowerNode);
  }
  this.inputLog = function() {
    this.inputTwoSons(LogNode);
  }
  this.inputBrackets = function() {
    this.inputOneSon(BracketsNode);
  }
  this.inputFraction = function() {
    this.inputTwoSons(FractionNode);
  }
  this.inputFactorial = function() {
    this.inputZeroSon(FactorialNode);
  }
  this.inputGcd = function() {
    this.inputTwoSons(GcdNode);
  }
  this.inputLcm = function() {
    this.inputTwoSons(LcmNode);
  }
  this.inputCombination = function() {
    this.inputTwoSons(CombinationNode);
  }
  this.inputPermutation = function() {
    this.inputTwoSons(PermutationNode);
  }
  this.inputEnd = function() {
    if (this.fatherPolynomial.length > 0) {
      let lastIndex = this.fatherPolynomial.length - 1;
      let fPolynomial = this.fatherPolynomial[lastIndex];
      let fBlock = this.fatherBlock[lastIndex];
      let fNode = this.fatherNode[lastIndex];
      if (this.inputPolynomial == fNode.son1 && fNode.son2 != null) {
        this.inputPolynomial = fNode.son2;
        this.inputBlock = this.inputPolynomial.blocks[0];
        this.inputNode = this.inputBlock.nodes[0];
      }
      else {
        this.inputPolynomial = fPolynomial;
        this.inputBlock = fBlock;
        this.inputNode = fNode;
        this.fatherPolynomial.splice(lastIndex, 1);
        this.fatherBlock.splice(lastIndex, 1);
        this.fatherNode.splice(lastIndex, 1);
      }
    }
  }
  this.inputPi = function() {
    this.inputZeroSon(PiNode);
  }
  this.inputE = function() {
    this.inputZeroSon(ENode);
  }
  this.inputX = function() {
    this.inputZeroSon(XNode);
  }
  this.inputEquation = function() {
    this.inputTwoSons(EquationNode);
  }

	this.toString = function() {
		return this.polynomial.toString();
	}
}