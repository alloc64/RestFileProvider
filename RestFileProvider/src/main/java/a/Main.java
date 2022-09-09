/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package a;

import com.alloc64.restfileprovider.RestFileProvider;

public class Main
{
    public static void main(String[] args)
    {
        RestFileProvider.get().start();
    }
}
