package io.catalyte.training.sportsproducts.domains.product;

public enum SearchOperation {
  EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;

  public static final String[] SIMPLE_OPERATION_SET = { ":", "!", ">", "<", "~" };

  public static final String OR_PREDICATE_FLAG = "'";

  public static final String ZERO_OR_MORE_REGEX = "*";

  public static final String OR_OPERATOR = "OR";

  public static final String AND_OPERATOR = "AND";

  public static final String LEFT_PARANTHESIS = "(";

  public static final String RIGHT_PARANTHESIS = ")";

  /**
   * parses symbols from search criteria to determine proper search operation
   * @param input symbol from search criteria
   * @return search operation
   */
  public static SearchOperation getSimpleOperation(final char input) {
    switch (input) {
      case ':':
        return EQUALITY;
      case '!':
        return NEGATION;
      case '>':
        return GREATER_THAN;
      case '<':
        return LESS_THAN;
      case '~':
        return CONTAINS;
      default:
        return null;
    }
  }
  }

