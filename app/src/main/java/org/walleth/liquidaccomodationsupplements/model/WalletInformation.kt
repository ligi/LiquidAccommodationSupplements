package org.walleth.liquidaccomodationsupplements.model

data class OnChainWalletInformation(val amount: String)

data class WalletInformation(val address: String,
                             val ethereumNodeUrl: String,
                             val hubContractAddress: String,
                             val hubProviderUrl: String,
                             val amount: String,
                             val onchain: OnChainWalletInformation)