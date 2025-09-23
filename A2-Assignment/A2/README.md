# A2 Genetic Algorithms

Your readme should include the following information. Each student needs to submit all of this information themselves, even when pair programming. 

Group Members: Chiashi, Kiara, Victoria, Shakila. 

Any peers and/or TAs/Tutors you collaborated with:

Would you like to give "kudos" to anyone who was particularly supportive or helpful?

Cite any references used:

If you used AI, please describe how you used it and what the experienc taught you:


## Questions

Please briefly describe what you observed about the "winners" produced by your genetic algorithm. Did changing the parameter values have any effect on what you observed?


## Reflection

Please provide a reflection on your experience with this assignment-- what was interesting? what was hard? what do you feel like you learned?
Shakila: The challenging part was making sure the offspring were built correctly in the evolve method. I had to carefully pick a random prefix from one parent and a random suffix from the other, combine them, and trim the result if it exceeded c_max. I also had to ensure that mutation was applied to each gene individually, not just once per offspring, by looping through the new chromosome and using the doesMutate method with the mutation rate m. And of course using ArrayList really helped here.
