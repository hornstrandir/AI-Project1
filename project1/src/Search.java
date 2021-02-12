import java.util.ArrayList;
import java.util.List;

public class Search {

	int playclock;
	Environment env;
	int[] returnMove = new int[4];

	public Search(Environment env, int playclock) {
		this.playclock = playclock;
		this.env = env;
	}




	public int[] iterativeDeepening(State state, int startingMillis){
		int depth = 1;
		while((int) System.currentTimeMillis() - startingMillis < (playclock - 1) * 1000){
			System.out.println("New iteration, depth: " + depth);
			returnMove = miniMaxRoot(state, depth, startingMillis);
			depth++;


		}
		return returnMove;
	}

	public int[] miniMaxRoot(State state, int depth, int startingMillis){
		if ((int) System.currentTimeMillis() - startingMillis < (playclock - 1) * 1000){

			int[] bestMove = new int[4];
			int maxEval = -101;
		
			List<int[]> legalMoves = new ArrayList<int[]>();
			legalMoves = env.legalMoves(state);

			if(legalMoves.isEmpty()){
				int[] illegalMove = {0,0,0,0};
				System.out.println("problem with miniMaxRoot");
				return illegalMove; //this makes no sense //gelichstand
			} else {
				for(int[] legalMove : legalMoves){	//TODO what happens when there are no legal moves?
				
					State child = new State();
					child =	env.getNextState(state, legalMove);

				
					int childEval = miniMax(child, depth -1, startingMillis);
					

					System.out.println("Move: from (" + legalMove[0] + "," + legalMove[1] + ") to (" + legalMove[2] + "," + legalMove[3] + ") Evaluation: " + childEval);			
					
					if(childEval > maxEval){
						maxEval = childEval;
						bestMove = legalMove;
						System.out.println("Best Move: Move: from (" + bestMove[0] + "," + bestMove[1] + ") to (" + bestMove[2] + "," + bestMove[3] + ") Evaluation: " + maxEval);
					}	
				
				}
			
			returnMove = bestMove;
			return bestMove;
			}



		} else {
			return returnMove;
		}
	}

	public int miniMax(State s, int depth, int startingMillis){
		if ((int) System.currentTimeMillis() - startingMillis < (playclock - 1) * 1000){

			State state = s.clone();
			List<int[]> legalMoves = new ArrayList<int[]>();
			legalMoves = env.legalMoves(state);



			if(depth == 0 || env.eval(state) == 100 || env.eval(state) == -100){
				//System.out.println(state.toString() + "    Evaluation: " + env.eval(state));	
				return env.eval(state);
			}
			if(legalMoves.isEmpty()){
				return 0; //gleichstand
			} else {
				//maximizing player
				if(state.myTurn){
					int maxEval = -101;
					for(int[] legalMove : legalMoves){
						State child = new State();
						child = env.getNextState(state, legalMove);
						int childEval = miniMax(child, depth -1, startingMillis);
						if ((int) System.currentTimeMillis() - startingMillis > (playclock - 1) * 1000){
							break;
						}
						if(childEval > maxEval){
							maxEval = childEval;
						}
					} 
					return maxEval; // will return the first childEval that is bigger than maxEval

				} else {
					//minimizing player
					int minEval = 101;
					for(int[] legalMove : legalMoves){
						State child = new State();
						child = env.getNextState(state, legalMove);
						int childEval = miniMax(child, depth -1, startingMillis);
						if ((int) System.currentTimeMillis() - startingMillis > (playclock - 1) * 1000){
							break;
						}
						if(childEval < minEval){
							minEval = childEval;
						}
					}

					return minEval; //returns the first eval that is smaller than 101

				}
			}
		} else {
			break; // break is useless. If else is true it doesnt need a break statement anymore
			System.out.println("problem with minimax breakstatement");
			return 0;
		}
	}
}
