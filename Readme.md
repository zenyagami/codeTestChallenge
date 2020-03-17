# Welcome to StackEdit!

Compiled in Android Studio 3.6.1 (Stable version)

# Description
Used MVVM, clean architecture, rxKotlin (co routines could have been used too but was not specified), Room for persistence,  mockito for Unit testing, Data binding

## Bugs or Improvements

Currently the sorting it's based on kotlin Comparable extension, however seems there is a bug and I could not extend this comparator, that means all the sorting will be Ascending. There is an option to use **thenByDescending ** but trying to make re usable it's not possible due the bug.
Example of the bug

    getDefaultComparator().apply {
                when (sortOption) {
                    SortOptions.BEST_MATCH -> thenBy { it.bestMatch }
                    SortOptions.AVERAGE_PRODUCT_PRICE -> thenByDescending { it.averageProductPrice }
                    SortOptions.DELIVERY_COST -> thenByDescending { it.deliveryCosts }
                    SortOptions.DISTANCE -> thenByDescending { it.distance ? }
                    SortOptions.MINIMUM_COST -> thenByDescending { it.minCost }
                    SortOptions.NEWEST_ITEM -> thenByDescending { it.newest }
                    SortOptions.POPULARITY -> thenBy { it.popularity }
                    SortOptions.RATING_AVERAGE -> thenByDescending { it.averageProductPrice }
                }
            }

The previous code will suit the order but there is a bug , and to avoid copy&paste for every possible sort I'm using my implementation

Another Improvement, move the sorting from the MainViewModel to another use case

Missing test for sorting 
