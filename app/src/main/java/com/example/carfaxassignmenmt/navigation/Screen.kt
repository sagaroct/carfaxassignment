package com.example.carfaxassignmenmt.navigation

/**
 * Created by Sagar Pujari on 18/02/23.
 */

abstract class Screen(val route: String) {

	fun withArgs(vararg args: String): String {
		return buildString {
			append(route)
			args.forEach { arg ->
				append("/$arg")
			}
		}
	}

	// build and setup route format (in navigation graph)
	fun withArgsFormat(vararg args: String): String {
		return buildString {
			append(route)
			args.forEach { arg ->
				append("/{$arg}")
			}
		}
	}
}



